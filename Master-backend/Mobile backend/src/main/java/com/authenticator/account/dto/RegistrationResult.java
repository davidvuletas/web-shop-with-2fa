package com.authenticator.account.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResult {
    private String applicationName;
    private String email;
}
