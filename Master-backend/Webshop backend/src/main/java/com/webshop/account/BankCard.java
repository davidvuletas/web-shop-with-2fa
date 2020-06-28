package com.webshop.account;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bank_card")
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Builder
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="card_number")
    private String cardNumber;

    @Column(name ="card_issuer")
    private CardIssuer cardIssuer;


}
