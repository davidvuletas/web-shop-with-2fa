package com.authenticator.account;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "account")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    @Enumerated
    private Role role;

    @Column(name = "generated_time")
    private String generatedTime;

}
