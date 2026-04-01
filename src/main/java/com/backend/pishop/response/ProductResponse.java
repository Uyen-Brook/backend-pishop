package com.backend.pishop.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.backend.pishop.enums.DiscountType;
import com.backend.pishop.enums.ProductStatus;

@Data
public class ProductResponse {
    private Long id;
    private String modelName;
    private String modelNumber;
    private String thumbnail;
    
    private BigDecimal price;
    private ProductStatus productStatus;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Integer quantity;
    
    private String description;
    private Map<String, String> specification; // JSON object
    private List<String> listImage;            // JSON array
    // query bangr productPromotion va Promotion de tinh gia giam phải thoa man đang dien ra, co hiêu lưc
    
    private Integer	discountPercent;
    private BigDecimal discountValue;
    private DiscountType discountType;
    private String promotionDescription;
    private String promotionName;
    // Thông tin liên quan đến Brand, Supplier, Category
    private String brandName;
    private String supplierName;
    private String categoryName;
}
