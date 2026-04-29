package com.backend.pishop.request;


import com.backend.pishop.enums.AccountRole;
import lombok.Data;

@Data
public class AccountRequest {

    private String firstName;
    private String lastName;

    private String email;
    private String password;

    private String image;
    
    private AccountRole role;
}