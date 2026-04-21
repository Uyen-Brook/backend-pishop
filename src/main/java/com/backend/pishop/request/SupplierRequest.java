package com.backend.pishop.request;

import lombok.Data;

@Data
public class SupplierRequest {
    private String name;
    private String taxcode;
    private String email;
    private String phone;
    private String address;
    private String note;
    private String website;
}