package com.backend.pishop.request;

import lombok.Data;
import java.math.BigDecimal;

import com.backend.pishop.enums.ProductStatus;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ProductUpdateRequest {

    private String modelName;
    private String modelNumber;
    private String description;

    private BigDecimal price;
    private Integer quantity;
  
    private BigDecimal importPrice;

    private ProductStatus productStatus;

    private Map<String, String> specification;

    // =========================
    // IMAGE HANDLING
    // =========================

    // ảnh mới upload (thêm)
    private List<MultipartFile> newImages;

    // ảnh xóa (URL từ frontend gửi lên)
    private List<String> deletedImages;

    // ảnh thumbnail mới (nếu thay)
    private MultipartFile thumbnail;

    // ảnh giữ lại (frontend gửi danh sách còn dùng)
    private List<String> keptImages;

    // =========================
    // RELATION
    // =========================
    private Long brandId;
    private Long supplierId;
    private Long categoryId;
}