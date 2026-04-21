package com.backend.pishop.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String firstName;
    private String lastName;
    private String email;
//    private String password;
    private LocalDate dob;
    private String phone;
    private boolean gender;
}