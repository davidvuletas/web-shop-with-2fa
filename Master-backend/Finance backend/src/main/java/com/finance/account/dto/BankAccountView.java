package com.finance.account.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BankAccountView {

    private Long id;
    private String owner;
    private String accountNumber;
    private List<CardView> cards;
}
