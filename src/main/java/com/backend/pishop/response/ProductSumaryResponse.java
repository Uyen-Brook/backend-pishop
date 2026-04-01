package com.backend.pishop.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.backend.pishop.enums.DiscountType;
import com.backend.pishop.enums.ProductStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class ProductSumaryResponse {
	private Long id;
    private String modelName;
    private String modelNumber;
    private String thumbnail;
    private BigDecimal price;
    private ProductStatus productStatus;
    private LocalDateTime createAt;
    private	LocalDateTime updateAt;
    private Integer quantity;
    
    private String promotionName;
    private DiscountType discountType;
    private BigDecimal discountValue;
    
    private String brandName;
    private String supplierName;
    private String categoryName;
   
}
