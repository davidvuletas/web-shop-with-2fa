package com.authenticator.account;

import com.authenticator.account.dto.AccountView;
import com.authenticator.config.security.DecryptionAlgorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    public void registerAccount(@RequestBody String qrCodeData) throws JsonProcessingException {
        String data = DecryptionAlgorithm.decryptData(qrCodeData);
        accountService.createAccountByQrCodeData(data);
        log.info(data);
    }

    @GetMapping
    public List<AccountView> getAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return accounts.stream().map(acc -> AccountView.builder()
                .id(acc.getId())
                .email(acc.getEmail())
                .role(acc.getRole().getRoleName())
                .dateOfCreation(acc.getGeneratedTime()).
                        build()).collect(Collectors.toList());
    }

}
