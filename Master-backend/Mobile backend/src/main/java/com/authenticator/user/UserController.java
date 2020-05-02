package com.authenticator.user;

import com.authenticator.config.security.JwtTokenUtil;
import com.authenticator.config.security.JwtUserDetailsService;
import com.authenticator.exception.InternalServerError;
import com.authenticator.user.dto.LoginForm;
import com.authenticator.user.dto.RegistrationForm;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@AllArgsConstructor
@CrossOrigin(value = "http://localhost:8080", exposedHeaders = "JWT")
@RequestMapping("/users")
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginForm loginInformation) {
        try {
            authenticate(loginInformation.getEmail(), loginInformation.getPassword());
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(loginInformation.getEmail());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("JWT", jwtTokenUtil.generateToken(userDetails));
            return ResponseEntity.ok().headers(httpHeaders).build();
        } catch (Exception e) {
            log.error("Failed login. Message: " + e.getMessage());
            throw new InternalServerError(e.getMessage());
        }
    }

    @PostMapping("/register")
    public void registration(@RequestBody RegistrationForm form) {
        User user = User.builder()
                .email(form.getEmail())
                .password(new BCryptPasswordEncoder().encode(form.getPassword()))
                .name(form.getName())
                .lastName(form.getLastName())
                .role(form.getRole())
                .twoFactorAuth(form.isTwoFactorAuth())
                .verified(form.isVerified())
                .build();
        userService.createUser(user);
    }


    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
