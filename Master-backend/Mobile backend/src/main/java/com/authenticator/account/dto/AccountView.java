package com.authenticator.account.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AccountView {
    private Long id;
    private String email;
    private String application;
    private String lastActivity;
}
