package com.backend.pishop.response;

import lombok.Data;

@Data
public class AddressResponse {
    private Long id;
    private String fullName;
    private String phone;
    private String specificAddress;
    private String provinceCode;
    private String wardCode;
    private String provinceName;
    private String wardName;
    private boolean isDefault;
}