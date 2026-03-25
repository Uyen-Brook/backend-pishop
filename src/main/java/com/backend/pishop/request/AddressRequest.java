package com.backend.pishop.request;

import lombok.Data;

@Data
public class AddressRequest {
    private String fullName;
    private String phone;
    private String specificAddress;
    private String provinceCode;
    private String wardCode;
}