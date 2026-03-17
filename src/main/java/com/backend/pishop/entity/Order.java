package com.backend.pishop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.backend.pishop.enums.OrderStatus;
import com.backend.pishop.enums.PayStatus;
import com.backend.pishop.enums.PaymentMethod;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "to_phone", nullable = false)
    private String toPhone;

    @Column(name = "to_name", nullable = false)
    private String toName;

    @Column(name = "to_address")
    private String toAddress;

    // địa chỉ hành chính
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_code")
    private Ward ward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_code")
    private Province province;

    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "ship_fee", precision = 12, scale = 2)
    private BigDecimal shipFee = BigDecimal.ZERO;

    @Column(name = "tracking_number")
    private String trackingNumber;

    // trạng thái đơn hàng
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    // phương thức thanh toán
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    // trạng thái thanh toán
    @Enumerated(EnumType.STRING)
    @Column(name = "pay_status")
    private PayStatus payStatus;

    @Column(name = "created_at")
    private LocalDateTime creatAt;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    // Order -> OrderItem
    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<OrderItem> items;
}