package com.backend.pishop.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.backend.pishop.entity.CartItem;
import com.backend.pishop.entity.Product;
import com.backend.pishop.entity.ProductPromotion;
import com.backend.pishop.entity.Promotion;
import com.backend.pishop.enums.DiscountType;
import com.backend.pishop.response.CartItemResponse;


@Component
public class CartMapper {
	
	public static CartItemResponse toResponse(CartItem item) {
        if (item == null) return null;

        Product product = item.getProduct();

        CartItemResponse dto = new CartItemResponse();

        dto.setProductId(product.getId());
        dto.setProductName(product.getModelName());
        dto.setThumbnail(product.getThumbnail());
        dto.setModelNumber(product.getModelNumber());
        dto.setQuantity(item.getQuantity());
        dto.setStockQuantity(product.getQuanlity());
        dto.setBasePrice(product.getPrice());

        // xử lý promotion + finalPrice
        fillActivePromotion(product, dto);

        return dto;
    }

    // ============================
    // PROMOTION HANDLER
    // ============================
    private static void fillActivePromotion(Product product, CartItemResponse dto) {

        if (product.getProductPromotions() == null || product.getProductPromotions().isEmpty()) {
            dto.setFinalPrice(product.getPrice());
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        Promotion active = product.getProductPromotions()
                .stream()
                .map(ProductPromotion::getPromotion)
                .filter(Objects::nonNull)
                .filter(Promotion::isActive)
                .filter(p -> p.getStartDate() == null || !p.getStartDate().atStartOfDay().isAfter(now))
                .filter(p -> p.getEndDate() == null || !p.getEndDate().atTime(23, 59, 59).isBefore(now))
                .findFirst()
                .orElse(null);

        BigDecimal basePrice = product.getPrice();
        BigDecimal finalPrice = basePrice;

        if (active != null) {

            if (active.getDiscountType() == DiscountType.PERCENT) {
                finalPrice = basePrice.subtract(
                        basePrice.multiply(active.getDiscountValue())
                                .divide(BigDecimal.valueOf(100))
                );
            } else {
                finalPrice = basePrice.subtract(active.getDiscountValue());
            }

            dto.setPromotionName(active.getName());
            dto.setDiscountType(active.getDiscountType());
            dto.setDiscountValue(active.getDiscountValue());
            dto.setStartDate(active.getStartDate());
            dto.setEndDate(active.getEndDate());
        }

        dto.setFinalPrice(finalPrice);
    }
}

