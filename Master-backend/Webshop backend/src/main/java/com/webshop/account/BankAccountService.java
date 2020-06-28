package com.webshop.account;

import com.webshop.account.dto.BankAccountView;
import com.webshop.account.dto.CardView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public List<BankAccountView> mapBankAccountsToBankAccountView(List<BankAccount> bankAccounts) {
        return bankAccounts.stream().map(bankAccount -> BankAccountView.builder()
                .accountNumber(bankAccount.getAccountNumber())
                .id(bankAccount.getId())
                .owner(bankAccount.getUser().getName() + " " + bankAccount.getUser().getLastName())
                .cards(bankAccount.getBankCardss()
                        .stream().map(card -> CardView.builder()
                                .cardIssuer(card.getCardIssuer())
                                .cardNumber(card.getCardNumber())
                                .id(card.getId()).build()).collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }
}
