package com.backend.pishop.response;


import com.backend.pishop.enums.AccountRank;
import com.backend.pishop.enums.AccountRole;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountResponse {

    private Long id;

    private String firstName;
    private String lastName;

    private String email;

    @JsonProperty("isActive")
    private boolean active;

    private String image;
    private AccountRole role;
    private Integer point;
    private AccountRank rank;

    private LocalDateTime createAt;
    private LocalDateTime lastActivity;
}