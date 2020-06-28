package com.webshop.account.dto;

import com.webshop.account.CardIssuer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardView {

    private Long id;
    private CardIssuer cardIssuer;
    private String cardNumber;
}
