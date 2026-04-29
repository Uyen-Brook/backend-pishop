package com.backend.pishop.mapper;

import org.springframework.stereotype.Component;

import com.backend.pishop.entity.Order;
import com.backend.pishop.entity.OrderItem;
import com.backend.pishop.entity.Product;
import com.backend.pishop.entity.ProductPromotion;
import com.backend.pishop.entity.Promotion;
import com.backend.pishop.response.OrderResponse;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .toPhone(order.getToPhone())
                .toName(order.getToName())
                .toAddress(order.getToAddress())
                .wardName(order.getWard() != null ? order.getWard().getName() : null)
                .provinceName(order.getProvince() != null ? order.getProvince().getName() : null)
                .totalAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .voucherCode(order.getVoucherCode())
                .shipFee(order.getShipFee())
                .trackingNumber(order.getTrackingNumber())
                .orderStatus(order.getOrderStatus())
                .paymentMethod(order.getPaymentMethod())
                .payStatus(order.getPayStatus())
                .createAt(order.getCreateAt())
                .lastUpdate(order.getLastUpdate())
                .items(order.getItems()
                        .stream()
                        .map(this::mapOrderItem)
                        .toList())
                .build();
    }

    private OrderResponse.OrderItemResponse mapOrderItem(OrderItem item) {

        Product product = item.getProduct();

        Promotion promotion = null;

        if (product != null && item.getPromotionId() != null) {
            promotion = product.getProductPromotions()
                    .stream()
                    .map(ProductPromotion::getPromotion)
                    .filter(p -> p.getId().equals(item.getPromotionId()))
                    .findFirst()
                    .orElse(null);
        }

        return OrderResponse.OrderItemResponse.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .discountPrice(item.getDiscountPrice())
                .promotionId(item.getPromotionId())
                .product(mapProduct(product))
                .promotion(mapPromotion(promotion))
                .build();
    }

    private OrderResponse.ProductResponse mapProduct(Product product) {

        if (product == null) {
            return null;
        }

        return OrderResponse.ProductResponse.builder()
                .id(product.getId())
                .modelName(product.getModelName())
                .thumbnail(product.getThumbnail())
                .price(product.getPrice())
                .modelNumber(product.getModelNumber())
                .quanlity(product.getQuanlity())
                .productStatus(product.getProductStatus())
                .brandName(product.getBrand() != null
                        ? product.getBrand().getName()
                        : null)
                .categoryName(product.getCategory() != null
                        ? product.getCategory().getName()
                        : null)
                .build();
    }

    private OrderResponse.PromotionResponse mapPromotion(Promotion promotion) {

        if (promotion == null) {
            return null;
        }

        return OrderResponse.PromotionResponse.builder()
                .id(promotion.getId())
                .name(promotion.getName())
                .discountType(promotion.getDiscountType())
                .discountValue(promotion.getDiscountValue())
                .description(promotion.getDescription())
                .isActive(promotion.isActive())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .build();
    }
}