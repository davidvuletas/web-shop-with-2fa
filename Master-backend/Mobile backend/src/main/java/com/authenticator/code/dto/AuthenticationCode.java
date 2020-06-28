package com.authenticator.code.dto;

import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationCode {

    private String code;
    private String email;
    private String applicationName;
}
