package com.backend.pishop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.backend.pishop.enums.SerialStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "serial_numbers")
@Getter
@Setter
public class SerialNumber {

    @Id
    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    // trạng thái serial
    @Enumerated(EnumType.STRING)
    private SerialStatus status;

    @Column(name = "import_date")
    private LocalDateTime importDate;

    @Column(name = "import_price", precision = 12, scale = 2)
    private BigDecimal importPrice;

    @Column(name = "warehouse_location")
    private String warehouseLocation;

    @Column(name = "selling_price", precision = 12, scale = 2)
    private BigDecimal sellingPrice;

    @Column(name = "sold_date")
    private LocalDateTime soldDate;

    @Column(name = "warranty_period")
    private Integer warrantyPeriod; // tháng

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    // Serial -> Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // Serial -> Order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}