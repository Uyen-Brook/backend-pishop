package com.backend.pishop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
	    name = "product_promotions",
	    indexes = {
	        @Index(name = "idx_pp_product", columnList = "product_id"),
	        @Index(name = "idx_pp_promotion", columnList = "promotion_id"),
	        @Index(name = "idx_pp_product_promotion", columnList = "product_id,promotion_id")
	    }
	)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPromotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết với Promotion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    // Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}