package com.backend.pishop.request;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.backend.pishop.enums.DiscountType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionRequest {
    private String name;
    private DiscountType discountType; // PERCENT, FIXED_AMOUNT
    private BigDecimal discountValue;
    private String description;
    private boolean isActive;
    private LocalDate startDate;
    private LocalDate endDate;

    // Nếu muốn gán sản phẩm cho promotion ngay từ request
    private Set<Long> productIds;
}
