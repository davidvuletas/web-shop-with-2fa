package com.authenticator.account;

import com.authenticator.account.dto.AccountView;
import com.authenticator.account.dto.RegistrationResult;
import com.authenticator.code.QRCodesService;
import com.authenticator.config.security.DecryptionAlgorithm;
import com.authenticator.exception.AlreadyExistingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final QRCodesService qrCodesService;
    private final String destination = "/topic/messages/";

    @PostMapping("/register")
    public RegistrationResult registerAccount(@RequestBody String qrCodeData) throws JsonProcessingException {
        if(qrCodesService.existsByQRCode(qrCodeData)) {
            throw new AlreadyExistingException("QR Code already read!");
        }
        String applicationName = qrCodeData.split(";")[1].split(":")[1].trim();
        String data = DecryptionAlgorithm.decryptData(qrCodeData);
        Account account = accountService.createAccountByQrCodeData(data, applicationName);
        qrCodesService.createQRCode(qrCodeData);
        return RegistrationResult.builder().applicationName(applicationName).email(account.getEmail()).build();
    }

    @PostMapping("/checkAccount/{mail}")
    public void checkAccount(@RequestBody String qrCodeData, @PathVariable String mail) {
        if(qrCodesService.existsByQRCode(qrCodeData)) {
            throw new AlreadyExistingException("QR Code already read!");
        }
        String applicationName = qrCodeData.split(";")[1].split(":")[1].trim();
        String data = DecryptionAlgorithm.decryptData(qrCodeData);
        Account account = accountService.doValidation(data, mail, applicationName);
        qrCodesService.createQRCode(qrCodeData);
        simpMessagingTemplate.convertAndSend(destination + account.getEmail() + "/login","Verified");
        log.info("Message send via WebSocket for login !!!");
    }

    @GetMapping
    public List<AccountView> getAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return accounts.stream().map(acc -> AccountView.builder()
                .id(acc.getId())
                .email(acc.getEmail())
                .application(acc.getApplication())
                .lastActivity(acc.getLastActivity()).
                        build()).collect(Collectors.toList());
    }

}
