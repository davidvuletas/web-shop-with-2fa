package com.webshop.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_profile")
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "two_factor_auth")
    private boolean twoFactorAuth;

    @Column
    private boolean verified;
}
