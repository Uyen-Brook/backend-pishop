package com.backend.pishop.mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.backend.pishop.entity.Product;
import com.backend.pishop.entity.ProductPromotion;
import com.backend.pishop.entity.Promotion;
import com.backend.pishop.response.ProductResponse;
import com.backend.pishop.response.ProductSumaryResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class ProductMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    // product detail response
    public static ProductResponse toResponse(Product product) {
        if (product == null) return null;

        ProductResponse dto = new ProductResponse();

        dto.setId(product.getId());
        dto.setModelName(product.getModelName());
        dto.setModelNumber(product.getModelNumber());
        dto.setThumbnail(product.getThumbnail());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setProductStatus(product.getProductStatus());
        dto.setCreateAt(product.getCreateAt());
        dto.setUpdateAt(product.getUpdateAt());
        dto.setQuantity(product.getQuanlity()); // chú ý: entity viết sai chính tả

        // ===== Parse JSON → Map =====
        try {
            dto.setSpecification(
                mapper.readValue(product.getSpecification(), new TypeReference<Map<String, String>>() {})
            );
        } catch (Exception e) {
            dto.setSpecification(null);
        }

        // ===== Parse JSON → List =====
        try {
            dto.setListImage(
                mapper.readValue(product.getListImage(), new TypeReference<List<String>>() {})
            );
        } catch (Exception e) {
            dto.setListImage(null);
        }

        // ===== Relational fields =====
        dto.setBrandName(product.getBrand().getName());
        dto.setSupplierName(product.getSupplier().getName());
        dto.setCategoryName(product.getCategory().getName());

        // ===== Promotion đang hiệu lực =====
        fillActivePromotion(product, dto);

        return dto;
    }

    // PRODUCT SUMMARY RESPONSE
    public static ProductSumaryResponse toSummaryResponse(Product product) {
        if (product == null) return null;

        ProductSumaryResponse dto = new ProductSumaryResponse();

        dto.setId(product.getId());
        dto.setModelName(product.getModelName());
        dto.setModelNumber(product.getModelNumber());
        dto.setThumbnail(product.getThumbnail());
        dto.setPrice(product.getPrice());
        dto.setProductStatus(product.getProductStatus());
        dto.setCreateAt(product.getCreateAt());
        dto.setUpdateAt(product.getUpdateAt());
        dto.setQuantity(product.getQuanlity());

        dto.setBrandName(product.getBrand().getName());
        dto.setSupplierName(product.getSupplier().getName());
        dto.setCategoryName(product.getCategory().getName());

        // summary cũng có thể cần promotion
        fillActivePromotion(product, dto);

        return dto;
    }

    // ============================
    // PROMOTION HANDLER
    // ============================
    private static void fillActivePromotion(Product product, Object dto) {
        if (product.getProductPromotions() == null || product.getProductPromotions().isEmpty()) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        Promotion active = product.getProductPromotions()
                .stream()
                .map(ProductPromotion::getPromotion)
                .filter(Objects::nonNull)
                .filter(p -> p.getStartDate() != null && p.getEndDate() != null)
                .filter(p -> !p.getStartDate().atStartOfDay().isAfter(now))  // startDate <= now
                .filter(p -> !p.getEndDate().atTime(23, 59, 59).isBefore(now)) // endDate >= now
                .findFirst()
                .orElse(null);

        if (active == null) return;

        if (dto instanceof ProductResponse res) {
            res.setPromotionName(active.getName());
            res.setPromotionDescription(active.getDescription());
            res.setDiscountType(active.getDiscountType());
            res.setDiscountValue(active.getDiscountValue());
        }

        if (dto instanceof ProductSumaryResponse sum) {
            sum.setPromotionName(active.getName());
            sum.setDiscountType(active.getDiscountType());
            sum.setDiscountValue(active.getDiscountValue());
        }
    }
}
