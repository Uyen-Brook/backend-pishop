package com.backend.pishop.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    private String modelName;
    private String specification; // JSON string
    private String thumbnail;
    private String description;
    private BigDecimal importPrice;
    private BigDecimal taxVat;
    private BigDecimal price;
    private String modelNumber;
    private String listImage; // JSON string
    private Integer quanlity;
    private String productStatus;
    private Long brandId;
    private Long supplierId;
    private Long categoryId;
}
