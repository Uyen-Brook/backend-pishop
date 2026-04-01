package com.backend.pishop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.backend.pishop.enums.AccountRank;
import com.backend.pishop.enums.DiscountType;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "vouchers",
    indexes = {
        @Index(name = "idx_voucher_code", columnList = "voucher_code")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_code", nullable = false, unique = true)
    private String voucherCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false)
    private BigDecimal discountValue;

    @Column(name = "max_discount_amount")
    private BigDecimal maxDiscountAmount;

    @Column(name = "min_order_value")
    private BigDecimal minOrderValue;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "current_usage", nullable = false)
    @Builder.Default
    private Integer currentUsage = 0;

    @Column(name = "per_user_limit")
    private Integer perUserLimit;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "is_active")
    private boolean isActive;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "required_rank")
    private AccountRank requiredRank;
}
