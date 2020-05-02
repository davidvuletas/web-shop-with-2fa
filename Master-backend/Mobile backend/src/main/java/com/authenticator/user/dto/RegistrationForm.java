package com.authenticator.user.dto;

import com.authenticator.user.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationForm {

    private String email;
    private String password;
    private String name;
    private String lastName;
    private Role role;
    private boolean twoFactorAuth;
    private boolean verified;
}
