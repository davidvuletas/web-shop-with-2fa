package com.authenticator.account;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "account")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String application;

    @Column(name = "last_activity")
    private String lastActivity;

}
