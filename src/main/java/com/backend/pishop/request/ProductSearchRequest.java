package com.backend.pishop.request;

import java.math.BigDecimal;

import com.backend.pishop.enums.ProductStatus;

import lombok.Data;

@Data
public class ProductSearchRequest {

    private String keyword; // id + name

    private Long categoryId;
    private Long supplierId;
    private Long brandId;

    private Long promotionId; // lọc theo promotion cụ thể

    private BigDecimal minPrice;
    
    private BigDecimal maxPrice;

    private ProductStatus productStatus;
}