package com.backend.pishop.controller.admin;

import com.backend.pishop.entity.Product;
import com.backend.pishop.request.ProductCreateRequest;
import com.backend.pishop.request.ProductUpdateRequest;
import com.backend.pishop.response.ProductResponse;
import com.backend.pishop.response.ProductSumaryResponse;
import com.backend.pishop.service.admin.ProductAdminService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductAdminService productAdminService;

    // CREATE
   
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> createProduct(
            @ModelAttribute ProductCreateRequest request
    ) {
        return ResponseEntity.ok(productAdminService.createProduct(request));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateRequest request
    ) {
        return ResponseEntity.ok(productAdminService.updateProduct(id, request));
    }

    // GET DETAIL
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductDetail(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(productAdminService.getProductDetail(id));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            Pageable pageable
    ) {
        return ResponseEntity.ok(productAdminService.getAllProducts(pageable));
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponse>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long supplierId,
            @RequestParam(required = false) Boolean deleted,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                productAdminService.searchProducts(
                        keyword,
                        categoryId,
                        brandId,
                        supplierId,
                        deleted,
                        pageable
                )
        );
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteProduct(
            @PathVariable Long id
    ) {
        productAdminService.softDeleteProduct(id);
        return ResponseEntity.ok().build();
    }

    // HARD DELETE
    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteProduct(
            @PathVariable Long id
    ) {
        productAdminService.hardDeleteProduct(id);
        return ResponseEntity.ok().build();
    }

    // RESTORE
    @PatchMapping("/{id}/restore")
    public ResponseEntity<Void> restoreProduct(
            @PathVariable Long id
    ) {
        productAdminService.restoreProduct(id);
        return ResponseEntity.ok().build();
    }

    // CHANGE STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<ProductResponse> changeStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(
                productAdminService.changeStatus(id, status)
        );
    }
}