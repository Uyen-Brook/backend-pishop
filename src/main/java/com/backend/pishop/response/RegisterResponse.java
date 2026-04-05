package com.backend.pishop.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private Long accountId;
    private String email;
    private String role;
    private String token;
}
