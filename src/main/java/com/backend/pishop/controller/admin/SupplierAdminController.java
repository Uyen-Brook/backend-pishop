package com.backend.pishop.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.pishop.config.APIResponse;
import com.backend.pishop.request.SupplierRequest;
import com.backend.pishop.response.SupplierResponse;
import com.backend.pishop.service.SupplierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/suppliers")
@RequiredArgsConstructor
public class SupplierAdminController {

    private final SupplierService supplierService;

    // CREATE
    @PostMapping
    public APIResponse<SupplierResponse> create(
            @ModelAttribute SupplierRequest request,
            @RequestParam(required = false) MultipartFile logo
    ) {
        return APIResponse.<SupplierResponse>builder()
                .result(supplierService.create(request, logo))
                .build();
    }

    // UPDATE (PATCH)
    @PatchMapping("/{id}")
    public APIResponse<SupplierResponse> update(
            @PathVariable Long id,
            @ModelAttribute SupplierRequest request,
            @RequestParam(required = false) MultipartFile logo
    ) {
        return APIResponse.<SupplierResponse>builder()
                .result(supplierService.update(id, request, logo))
                .build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return APIResponse.<Void>builder().message("Deleted").build();
    }

    // GET ALL
    @GetMapping
    public APIResponse<List<SupplierResponse>> getAll() {
        return APIResponse.<List<SupplierResponse>>builder()
                .result(supplierService.getAll())
                .build();
    }

    // SEARCH
    @GetMapping("/search")
    public APIResponse<List<SupplierResponse>> search(
            @RequestParam(required = false) String keyword
    ) {
        return APIResponse.<List<SupplierResponse>>builder()
                .result(supplierService.search(keyword))
                .build();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public APIResponse<SupplierResponse> getById(@PathVariable Long id) {
        return APIResponse.<SupplierResponse>builder()
                .result(supplierService.getById(id))
                .build();
    }
}