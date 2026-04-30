package com.backend.pishop.response;


import java.time.LocalDateTime;

import com.backend.pishop.enums.DataStatus;
import com.backend.pishop.enums.DeviceAccountStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarrantyAcceptanceResponse {

    private Long id;

    private LocalDateTime startDate;

    // customer
    private String customerName;

    private String phone;

    private String email;

    // product
    private String serialNumber;

    private String productName;

    private String modelNumber;

    private String thumbnail;

    // machine status
    private String productStatus;

    private String problem;

    private String accessories;

    private DeviceAccountStatus accountStatus;

    private String password;

    private DataStatus dataStatus;

    private String estimate;

    // warranty info
    private Integer warrantyPeriod;

    private LocalDateTime soldDate;

    private Boolean inWarranty;
}