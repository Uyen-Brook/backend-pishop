package com.backend.pishop.entity;

import java.time.LocalDateTime;

import com.backend.pishop.enums.VoucherStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "voucher_usages",
    indexes = {
        @Index(name = "idx_voucher_user", columnList = "voucher_id, account_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 MANY TO ONE → Voucher
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;

    // 🔥 MANY TO ONE → Account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    @Column(name = "issue_date")
    private LocalDateTime IssueDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name ="voucher_status")
    private VoucherStatus voucherStatus;
    
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "used_at")
    private LocalDateTime usedAt;
}