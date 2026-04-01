package com.backend.pishop.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.backend.pishop.enums.DiscountType;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class CartItemResponse {

    private Long productId;
    private String productName;
    private String thumbnail;
    private String modelNumber;
    private Integer quantity;
    private Integer stockQuantity;

    private BigDecimal basePrice;
    private BigDecimal finalPrice;

    private String promotionName;
    private BigDecimal discountValue;
    private DiscountType discountType;
    private LocalDate startDate;
    private LocalDate endDate;
}
