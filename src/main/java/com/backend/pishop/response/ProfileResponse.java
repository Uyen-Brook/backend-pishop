package com.backend.pishop.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponse {
	private Long id;
    private String firstName;
    private String lastName;
    private String image;
    private LocalDateTime createdAt;
    private String phone;
    private String email;
    private Boolean gender;
}