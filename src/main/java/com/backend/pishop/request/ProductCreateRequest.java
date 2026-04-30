package com.backend.pishop.request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.backend.pishop.enums.ProductStatus;

import java.util.List;

@Data
public class ProductCreateRequest {
    private String modelName;
    private String specification; // JSON string
    private String description;
    private BigDecimal importPrice;
    private BigDecimal taxVat;
    private BigDecimal price;
    private String modelNumber;
    private MultipartFile thumbnail;     // ảnh chính
    private List<MultipartFile> listImage; // nhiều ảnh p
    private Integer quantity;
    private ProductStatus productStatus;
    private Long brandId;
    private Long supplierId;
    private Long categoryId;
}
