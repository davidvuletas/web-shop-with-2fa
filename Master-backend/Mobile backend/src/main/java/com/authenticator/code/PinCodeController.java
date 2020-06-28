package com.authenticator.code;

import com.authenticator.account.Account;
import com.authenticator.account.AccountService;
import com.authenticator.code.dto.AuthenticationCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/code")
public class PinCodeController {

    private final PinCodeService pinCodeService;
    private final AccountService accountService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final String destination = "/topic/messages/";

    @PostMapping
    public void createAuthenticationCode(@RequestBody AuthenticationCode authenticationCode) {
        pinCodeService.storeAuthenticationCode(PinCode.builder()
                .code(authenticationCode.getCode())
                .application(authenticationCode.getApplicationName())
                .email(authenticationCode.getEmail()).build());
    }

    @PostMapping("/valid")
    public boolean isCodeValidForMail(@RequestBody AuthenticationCode code) {
        boolean exists = pinCodeService.existsByCodeAndMail(code.getCode(), code.getEmail(), code.getApplicationName());
        if(exists) {
            Account account = accountService.getAccountByMailAndApplicationName(code.getEmail(), code.getApplicationName());
            simpMessagingTemplate.convertAndSend(destination + account.getEmail() + "/register","Verified");
            log.info("Message send via WebSocket for registration !!!");
        }
        return exists;
    }
}
