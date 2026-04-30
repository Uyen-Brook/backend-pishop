package com.backend.pishop.controller.admin;

import com.backend.pishop.request.PromotionRequest;
import com.backend.pishop.response.PromotionResponse;
import com.backend.pishop.service.admin.PromotionService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/promotions")
@RequiredArgsConstructor
public class PromotionAdminController {

    private final PromotionService promotionService;

    // CREATE
    @PostMapping
    public PromotionResponse create(@RequestBody PromotionRequest request) {
        return promotionService.addPromotion(request);
    }

    // UPDATE
    @PatchMapping("/{id}")
    public PromotionResponse update(
            @PathVariable Long id,
            @RequestBody PromotionRequest request
    ) {
        return promotionService.updatePromotion(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        promotionService.deletePromotion(id);
        return "Deleted";
    }

    // GET ALL
    @GetMapping
    public List<PromotionResponse> getAll() {
        return promotionService.getAllPromotions();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public PromotionResponse getById(@PathVariable Long id) {
        return promotionService.getPromotionById(id);
    }

    // ASSIGN PROMOTION TO PRODUCT
    @PostMapping("/{promotionId}/assign/{productId}")
    public String assignPromotionToProduct(
            @PathVariable Long promotionId,
            @PathVariable Long productId
    ) {
        promotionService.assignPromotionToProduct(
                promotionId,
                productId
        );

        return "Promotion assigned to product";
    }
}