package com.backend.pishop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // số lượng
    @Column(nullable = false)
    private Integer quantity;

    // giá tại thời điểm đặt
    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;

    // OrderItem -> Order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // OrderItem -> Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}