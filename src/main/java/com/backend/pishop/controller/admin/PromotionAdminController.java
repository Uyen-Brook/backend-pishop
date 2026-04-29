package com.backend.pishop.controller.admin;

import com.backend.pishop.config.APIResponse;
import com.backend.pishop.request.PromotionRequest;
import com.backend.pishop.response.DashboardResponse;
import com.backend.pishop.response.PromotionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.backend.pishop.service.admin.PromotionService;
@RestController
@RequestMapping("/admin/promotions")
@RequiredArgsConstructor
public class PromotionAdminController {

    private final PromotionService promotionService;

    // CREATE
    @PostMapping
    public APIResponse<PromotionResponse> create(@RequestBody PromotionRequest request) {
        return APIResponse.<PromotionResponse>builder()
                .result(promotionService.addPromotion(request))
                .build();
    }

    // UPDATE
    @PatchMapping("/{id}")
    public APIResponse<PromotionResponse> update(@PathVariable Long id, @RequestBody PromotionRequest request) {
        return APIResponse.<PromotionResponse>builder()
                .result(promotionService.updatePromotion(id, request))
                .build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        promotionService.deletePromotion(id);
        return APIResponse.<Void>builder().message("Deleted").build();
    }

    // GET ALL
    @GetMapping
    public APIResponse<List<PromotionResponse>> getAll() {
        return APIResponse.<List<PromotionResponse>>builder()
                .result(promotionService.getAllPromotions())
                .build();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public APIResponse<PromotionResponse> getById(@PathVariable Long id) {
        return APIResponse.<PromotionResponse>builder()
                .result(promotionService.getPromotionById(id))
                .build();
    }

    // ASSIGN PROMOTION TO PRODUCT
    @PostMapping("/{promotionId}/assign/{productId}")
    public APIResponse<Void> assignPromotionToProduct(@PathVariable Long promotionId, @PathVariable Long productId) {
        promotionService.assignPromotionToProduct(promotionId, productId);
        return APIResponse.<Void>builder().message("Promotion assigned to product").build();
    }

//    // DASHBOARD STATISTICS
//    @GetMapping("/dashboard")
//    public APIResponse<DashboardResponse> getDashboardStatistics() {
//        return APIResponse.<DashboardResponse>builder()
//                .result(promotionService.getDashboardStatistics())
//                .build();
//    }
    
}
