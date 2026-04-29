package com.backend.pishop.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.backend.pishop.enums.DiscountType;
import com.backend.pishop.enums.OrderStatus;
import com.backend.pishop.enums.PayStatus;
import com.backend.pishop.enums.PaymentMethod;
import com.backend.pishop.enums.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderResponse {

    private Long id;

    private String toPhone;

    private String toName;

    private String toAddress;

    private String wardName;

    private String provinceName;

    private BigDecimal totalAmount;

    private BigDecimal discountAmount;

    private String voucherCode;

    private BigDecimal shipFee;

    private String trackingNumber;

    private OrderStatus orderStatus;

    private PaymentMethod paymentMethod;

    private PayStatus payStatus;

    private LocalDateTime createAt;

    private LocalDateTime lastUpdate;

    private List<OrderItemResponse> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemResponse {

        private Long id;

        private Integer quantity;

        // giá gốc tại thời điểm mua
        private BigDecimal price;

        // giá sau giảm
        private BigDecimal discountPrice;

        private Long promotionId;

        private ProductResponse product;

        private PromotionResponse promotion;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductResponse {

        private Long id;

        private String modelName;

        private String thumbnail;

        private BigDecimal price;

        private String modelNumber;

        private Integer quanlity;

        private ProductStatus productStatus;

        private String brandName;

        private String categoryName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PromotionResponse {

        private Long id;

        private String name;

        private DiscountType discountType;

        private BigDecimal discountValue;

        private String description;

        private Boolean isActive;

        private LocalDate startDate;

        private LocalDate endDate;
    }
}