//package com.backend.pishop.mapper;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//import org.springframework.stereotype.Component;
//
//import com.backend.pishop.entity.CartItem;
//import com.backend.pishop.entity.Product;
//import com.backend.pishop.entity.ProductPromotion;
//import com.backend.pishop.entity.Promotion;
//import com.backend.pishop.enums.DiscountType;
//import com.backend.pishop.response.CartItemResponse;
//
//@Component
//public class CartMapper {
//
//    public CartItemResponse toItemResponse(CartItem item) {
//
//        Product product = item.getProduct();
//
//        // Lấy promotion hợp lệ
//        Promotion promo = product.getProductPromotions().stream()
//                .map(ProductPromotion::getPromotion)
//                .filter(p -> p.isActive()
//                        && (p.getStartDate() == null || !p.getStartDate().isAfter(LocalDate.now()))
//                        && (p.getEndDate() == null || !p.getEndDate().isBefore(LocalDate.now())))
//                .findFirst()
//                .orElse(null);
//
//        BigDecimal basePrice = product.getPrice();
//        BigDecimal finalPrice = basePrice;
//
//        if (promo != null) {
//            if (promo.getDiscountType() == DiscountType.PERCENT) {
//                finalPrice = basePrice.subtract(
//                        basePrice.multiply(promo.getDiscountValue())
//                                .divide(BigDecimal.valueOf(100))
//                );
//            } else {
//                finalPrice = basePrice.subtract(promo.getDiscountValue());
//            }
//        }
//
//        return CartItemResponse.builder()
//                .productId(product.getId())
//                .productName(product.getModelName())
//                .thumbnail(product.getThumbnail())
//                .modelNumber(product.getModelNumber())
//                .quantity(item.getQuantity())
//                .stockQuantity(product.getQuanlity()) // tồn kho
//                .basePrice(basePrice)
//                .finalPrice(finalPrice)
//                .promotionName(promo != null ? promo.getName() : null)
//                .discountValue(promo != null ? promo.getDiscountValue() : null)
//                .discountType(promo != null ? promo.getDiscountType() : null)
//                .startDate(promo != null ? promo.getStartDate() : null)
//                .endDate(promo != null ? promo.getEndDate() : null)
//                .build();
//    }
//}
//
