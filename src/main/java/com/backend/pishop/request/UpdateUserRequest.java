package com.backend.pishop.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private LocalDate dob;
    private String phone;
    private boolean gender;
}