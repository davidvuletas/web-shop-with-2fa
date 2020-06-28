package com.finance.user;

import com.finance.config.security.JwtTokenUtil;
import com.finance.config.security.JwtUserDetailsService;
import com.finance.exception.InternalServerError;
import com.finance.mail.MailService;
import com.finance.user.dto.LoginForm;
import com.finance.user.dto.RegistrationForm;
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

import java.util.Random;

@Log4j2
@RestController
@AllArgsConstructor
@CrossOrigin(value = "https://localhost:8080", exposedHeaders = "JWT")
@RequestMapping("/users")
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final MailService mailService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginForm loginInformation) {
        try {
            authenticate(loginInformation.getEmail(), loginInformation.getPassword());
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(loginInformation.getEmail());
            userService.isUserVerified(loginInformation.getEmail());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("JWT", jwtTokenUtil.generateToken(userDetails));
            return ResponseEntity.ok().headers(httpHeaders).body(userService.getUserByMail(loginInformation.getEmail()));
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
                .twoFactorAuth(form.isTwoFactorAuth())
                .verified(form.isVerified())
                .build();
        userService.createUser(user);
        if(form.isTwoFactorAuth()) {
            //TODO Call Authenticator backend
            String code =  String.format("%04d", new Random().nextInt(10000));
            mailService.sendMail(user.getEmail(), code);
        }
    }
    @PutMapping("/verified/{email}")
    public void makeUserVerified(@PathVariable String email) {
        User user = userService.getUserByMail(email);
        user.setVerified(true);
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
