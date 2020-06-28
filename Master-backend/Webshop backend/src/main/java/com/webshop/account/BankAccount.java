package com.webshop.account;

import com.webshop.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bank_account")
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Builder
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @OneToMany
    private List<BankCard> bankCardss;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column
    private Float balance;


}
