package com.backend.pishop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.pishop.entity.Product;
import com.backend.pishop.request.ProductCreateRequest;
import com.backend.pishop.request.ProductUpdateRequest;
import com.backend.pishop.service.admin.ProductAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {
    @Autowired
    private final ProductAdminService productAdminService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductCreateRequest request) {
        Product created = productAdminService.createProduct(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        Product updated = productAdminService.updateProduct(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteProduct(@PathVariable Long id) {
        productAdminService.softDeleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteProduct(@PathVariable Long id) {
        productAdminService.hardDeleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<Void> restoreProduct(@PathVariable Long id) {
        productAdminService.restoreProduct(id);
        return ResponseEntity.ok().build();
    }
}