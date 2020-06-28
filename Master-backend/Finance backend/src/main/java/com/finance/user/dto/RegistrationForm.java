package com.finance.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationForm {

    private String email;
    private String password;
    private String name;
    private String lastName;
    private boolean twoFactorAuth;
    private boolean verified;
}
