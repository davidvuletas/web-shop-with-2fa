package com.authenticator.code;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "authentication_code")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class PinCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4)
    private String code;

    @Column
    private String email;

    private String application;
}
