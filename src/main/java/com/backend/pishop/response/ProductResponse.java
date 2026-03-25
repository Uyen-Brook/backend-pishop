package com.backend.pishop.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ProductResponse {
    private Integer id;
    private String modelName;
    private String modelNumber;
    private String thumbnail;
    
    private BigDecimal basePrice;
    private String productStatus;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    
    private String description;
    private Map<String, String> specification; // JSON object
    private List<String> listImage;            // JSON array

    private String brandName;
    private String supplierName;
    private String categoryName;
}
