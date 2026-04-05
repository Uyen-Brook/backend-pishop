package com.backend.pishop.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthResponse {
    private String token;
    private AccountInfo account;

    @Data
    public static class AccountInfo {
        private Long id;
        private String email;
        private String role;
    }
}