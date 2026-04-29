package com.backend.pishop.service.admin;

import com.backend.pishop.entity.Promotion;
import com.backend.pishop.entity.Product;
import com.backend.pishop.entity.ProductPromotion;
import com.backend.pishop.repository.PromotionRepository;
import com.backend.pishop.repository.ProductRepository;
import com.backend.pishop.repository.ProductPromotionRepository;

import com.backend.pishop.request.PromotionRequest;
import com.backend.pishop.response.PromotionResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPromotionRepository productPromotionRepository;

    // Thêm mới một Promotion
    public PromotionResponse addPromotion(PromotionRequest promotionReq) {
        Promotion promotion = Promotion.builder()
                .name(promotionReq.getName())
                .discountType(promotionReq.getDiscountType())
                .discountValue(promotionReq.getDiscountValue())
                .description(promotionReq.getDescription())
                .isActive(promotionReq.isActive())
                .startDate(promotionReq.getStartDate())
                .endDate(promotionReq.getEndDate())
                .build();
        Promotion saved = promotionRepository.save(promotion);

        // if productIds provided, assign
        if (promotionReq.getProductIds() != null && !promotionReq.getProductIds().isEmpty()) {
            for (Long pid : promotionReq.getProductIds()) {
                productRepository.findById(pid).ifPresent(p -> {
                    ProductPromotion pp = ProductPromotion.builder().promotion(saved).product(p).build();
                    productPromotionRepository.save(pp);
                });
            }
        }
        return toResponse(saved);
    }

    // Cập nhật promotion
    public PromotionResponse updatePromotion(Long id, PromotionRequest promotionReq) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Promotion not found"));
        promotion.setName(promotionReq.getName());
        promotion.setDiscountType(promotionReq.getDiscountType());
        promotion.setDiscountValue(promotionReq.getDiscountValue());
        promotion.setDescription(promotionReq.getDescription());
        promotion.setActive(promotionReq.isActive());
        promotion.setStartDate(promotionReq.getStartDate());
        promotion.setEndDate(promotionReq.getEndDate());
        Promotion saved = promotionRepository.save(promotion);

        // replace product mappings if provided
        if (promotionReq.getProductIds() != null) {
            productPromotionRepository.deleteByPromotionId(saved.getId());
            for (Long pid : promotionReq.getProductIds()) {
                productRepository.findById(pid).ifPresent(p -> {
                    ProductPromotion pp = ProductPromotion.builder().promotion(saved).product(p).build();
                    productPromotionRepository.save(pp);
                });
            }
        }
        return toResponse(saved);
    }

    // Xóa promotion
    public void deletePromotion(Long id) {
        // delete mappings first
        productPromotionRepository.deleteByPromotionId(id);
        promotionRepository.deleteById(id);
    }

    // Lấy danh sách tất cả promotion
    public List<PromotionResponse> getAllPromotions() {
        return promotionRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Lấy theo id
    public PromotionResponse getPromotionById(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Promotion not found"));
        return toResponse(promotion);
    }

    // Gán Promotion cho một Product
    public ProductPromotion assignPromotionToProduct(Long promotionId, Long productId) {
        Optional<Promotion> promotionOpt = promotionRepository.findById(promotionId);
        Optional<Product> productOpt = productRepository.findById(productId);

        if (promotionOpt.isPresent() && productOpt.isPresent()) {
            ProductPromotion productPromotion = ProductPromotion.builder()
                    .promotion(promotionOpt.get())
                    .product(productOpt.get())
                    .build();
            return productPromotionRepository.save(productPromotion);
        } else {
            throw new IllegalArgumentException("Promotion or Product not found");
        }
    }

    // Gán Promotion cho nhiều Product
    public void assignPromotionToMultipleProducts(Long promotionId, List<Long> productIds) {
        Optional<Promotion> promotionOpt = promotionRepository.findById(promotionId);

        if (promotionOpt.isPresent()) {
            Promotion promotion = promotionOpt.get();
            for (Long productId : productIds) {
                Optional<Product> productOpt = productRepository.findById(productId);
                if (productOpt.isPresent()) {
                    ProductPromotion productPromotion = ProductPromotion.builder()
                            .promotion(promotion)
                            .product(productOpt.get())
                            .build();
                    productPromotionRepository.save(productPromotion);
                } else {
                    throw new IllegalArgumentException("Product with ID " + productId + " not found");
                }
            }
        } else {
            throw new IllegalArgumentException("Promotion not found");
        }
    }

    private PromotionResponse toResponse(Promotion p) {
        PromotionResponse resp = PromotionResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .discountType(p.getDiscountType())
                .discountValue(p.getDiscountValue())
                .description(p.getDescription())
                .isActive(p.isActive())
                .startDate(p.getStartDate())
                .endDate(p.getEndDate())
                .build();
        // collect product ids
        if (p.getProductPromotions() != null) {
            Set<Long> ids = p.getProductPromotions().stream().map(pp -> pp.getProduct().getId()).collect(Collectors.toSet());
            resp.setProductIds(ids);
        }
        return resp;
    }
}