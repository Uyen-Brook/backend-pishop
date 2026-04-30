package com.backend.pishop.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SerialNumberRequest {

    private String serialNumber;

    private Long productId;

//    private BigDecimal importPrice;
//
//    private String warehouseLocation;
//
//    private BigDecimal sellingPrice;

    // tháng bảo hành
    private Integer warrantyPeriod;
}