package com.backend.pishop.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductSumaryResponse {
	private Integer id;
    private String modelName;
    private String modelNumber;
    private String thumbnail;
    private BigDecimal basePrice;
    private String productStatus;
    private LocalDateTime createAt;
    private	LocalDateTime updateAt;
    
    private String brandName;
    private String supplierName;
    private String categoryName;
}
