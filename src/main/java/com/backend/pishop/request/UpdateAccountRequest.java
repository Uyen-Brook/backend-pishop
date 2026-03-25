package com.backend.pishop.request;

import lombok.Data;

@Data
public class UpdateAccountRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean gender;
}