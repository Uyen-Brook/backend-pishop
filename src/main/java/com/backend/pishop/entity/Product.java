package com.backend.pishop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "model_name", nullable = false)
    private String modelName;

    private String specific;

    private String thumbnail;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "base_price", precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "model_number")
    private String modelNumber;

    @Column(name = "list_image", columnDefinition = "TEXT")
    private String listImage;

    @Column(name = "create_at")
    private LocalDateTime creatAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    // Product -> Brand
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    // Product -> Supplier
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}