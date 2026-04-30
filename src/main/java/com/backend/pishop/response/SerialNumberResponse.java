package com.backend.pishop.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.backend.pishop.enums.SerialStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SerialNumberResponse {

    private String serialNumber;

    private SerialStatus status;

//    private LocalDateTime importDate;
//
//    private BigDecimal importPrice;
//
//    private String warehouseLocation;
//
//    private BigDecimal sellingPrice;

    private LocalDateTime soldDate;

    private Integer warrantyPeriod;

    private LocalDateTime createAt;

    private LocalDateTime lastUpdate;

    // product
    private Long productId;

    private String modelName;

    private String modelNumber;

    private String thumbnail;

    private String brandName;

    private String categoryName;

    // order
    private Long orderItemId;
}