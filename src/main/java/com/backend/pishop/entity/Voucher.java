package com.backend.pishop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Voucher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String voucherCode;       // Mã voucher (có thể null nếu là voucher cá nhân)
    private String voucherType;       // public, personal, shop, shopee, freeship, new_user

    private String discountType;      // percent, fixed_amount
    private BigDecimal discountValue; // 10%, 50k...

    private BigDecimal maxDiscountAmount; // Giới hạn giảm tối đa
    private BigDecimal minOrderValue;     // Điều kiện đơn hàng tối thiểu

    private Integer usageLimit;       // Tổng số lượt toàn hệ thống
    private Integer currentUsage;     // Đã dùng bao nhiêu lượt

    private Integer perUserLimit;     // Mỗi user được dùng bao nhiêu lần

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String status;            // active, inactive, expired
}
