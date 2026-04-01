package com.backend.pishop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.backend.pishop.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model_name", nullable = false)
    private String modelName;
    
    @Column(name ="specification", columnDefinition = "JSON")// cặp key value, ví dụ: {"RAM": "8GB", "Storage": "256GB", "Color": "Black"}
    private String specification;

    private String thumbnail;

    @Column(columnDefinition = "TEXT")// giống file html hoặc text để lưu mô tả chi tiết sản phẩm, có thể bao gồm cả hình ảnh, video trình bày về sản phẩm...
    private String description;

    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "model_number")
    private String modelNumber;

    @Column(name = "list_image", columnDefinition = "TEXT")
    private String listImage;
    
    @Column(name = "quanlity", nullable = false)
    private Integer quanlity;
    
    
    @Enumerated(EnumType.STRING)
    @Column(name = "product_status")
    private ProductStatus productStatus;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    // Product -> Brand
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    // Product -> Supplier
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    // Product -> Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    // promotion
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductPromotion> productPromotions = new HashSet<>();

}
