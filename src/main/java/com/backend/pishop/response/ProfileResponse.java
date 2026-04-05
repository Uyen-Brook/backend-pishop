package com.backend.pishop.response;

import java.time.LocalDateTime;

import com.backend.pishop.enums.AccountRank;

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
    private Integer point;
    private AccountRank rank;
    private String phone;
    private String email;
    private Boolean gender;
    
}