package com.backend.pishop.request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.backend.pishop.enums.ProductStatus;

import java.util.List;

@Data
public class ProductCreateRequest {
	
    private String modelName;//1
    private String specification; // JSON string
    private String description;//5
    private BigDecimal importPrice;//4
    private BigDecimal taxVat;
    private BigDecimal price;//3
    
    private String modelNumber;//2
    private MultipartFile thumbnail;     // ảnh chính
    private List<MultipartFile> listImage; // nhiều ảnh p
    private Integer quantity;//6
    private ProductStatus productStatus;//7
    private Long brandId;
    private Long supplierId;
    private Long categoryId;
}
