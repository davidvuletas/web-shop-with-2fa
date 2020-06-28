package com.authenticator.code;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "qr_codes")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class ReadQrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "read_time")
    private String readTime;
}
