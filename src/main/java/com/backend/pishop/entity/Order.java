package com.backend.pishop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "to_phone", nullable = false)
    private String toPhone;

    @Column(name = "to_name", nullable = false)
    private String toName;

    @Column(name = "to_address")
    private String toAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_code")
    private Ward ward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_code")
    private Province province;
    // tông tiền trước khi áp dụng voucher
    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount;
    // tiền giảm giá từ voucher
    @Column(name = "discount_amount", precision = 12, scale = 2)
    private BigDecimal discountAmount;
    // ma voucher đã áp dụng, lưu để dễ dàng truy vấn và hiển thị lịch sử đơn hàng
    @Column(name = "voucher_code")
    private String voucherCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
    
    @Column(name = "ship_fee", precision = 12, scale = 2)
    private BigDecimal shipFee = BigDecimal.ZERO;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_status")
    private PayStatus payStatus;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    // Quan hệ 1-N với PaymentTransaction
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PaymentTransaction> transactions = new ArrayList<>();
}
