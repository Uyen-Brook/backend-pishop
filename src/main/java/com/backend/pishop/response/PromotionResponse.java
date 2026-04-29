package com.backend.pishop.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.backend.pishop.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionResponse {
    private Long id;
    private String name;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private String description;
    private boolean isActive;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<Long> productIds;

}
