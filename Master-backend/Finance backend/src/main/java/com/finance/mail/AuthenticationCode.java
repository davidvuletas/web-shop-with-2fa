package com.finance.mail;

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
