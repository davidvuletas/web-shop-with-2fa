package com.webshop.account;

import com.webshop.account.dto.BankAccountView;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
@CrossOrigin(value = "https://localhost:8089")
@RequestMapping("/bank-account")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping
    public List<BankAccountView> getAllAccounts() {
        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccounts();
        return bankAccountService.mapBankAccountsToBankAccountView(bankAccounts);
    }
}
