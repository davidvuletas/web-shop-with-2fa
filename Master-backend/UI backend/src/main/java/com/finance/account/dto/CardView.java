package com.finance.account.dto;

import com.finance.account.CardIssuer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardView {

    private Long id;
    private CardIssuer cardIssuer;
    private String cardNumber;
}
