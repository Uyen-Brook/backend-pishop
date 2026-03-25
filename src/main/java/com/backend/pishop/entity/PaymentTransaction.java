package com.backend.pishop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.backend.pishop.enums.PaymentMethod;
import com.backend.pishop.enums.TransactionStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết với Order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod; // VNPAY, COD, BANK_TRANSFER...

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status", nullable = false)
    private TransactionStatus transactionStatus; // SUCCESS, FAILED, PENDING

    @Column(name = "transaction_no")
    private String transactionNo; // Mã giao dịch từ VNPAY

    @Column(name = "order_info")
    private String orderInfo; // Nội dung đơn hàng gửi sang VNPAY

    @Column(name = "response_code")
    private String responseCode; // Mã phản hồi từ VNPAY

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "pay_date")
    private LocalDateTime payDate;

    @Column(name = "sandbox_env")
    private Boolean sandboxEnv = true; // true nếu giao dịch ở môi trường demo

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
}

