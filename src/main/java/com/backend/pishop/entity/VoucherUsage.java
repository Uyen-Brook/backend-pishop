package com.backend.pishop.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
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
public class VoucherUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_id", nullable = false)
    private Long voucherId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "used_at")
    private LocalDateTime usedAt;
}