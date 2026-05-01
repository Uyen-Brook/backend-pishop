package com.backend.pishop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.pishop.response.BrandResponse;
import com.backend.pishop.response.CategoryResponse;
import com.backend.pishop.response.ProductResponse;
import com.backend.pishop.response.ProductSumaryResponse;
import com.backend.pishop.response.SupplierDetailResponse;
import com.backend.pishop.response.SupplierResponse;
import com.backend.pishop.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    // ============================
    // PRODUCTS
    // ============================

    // GET ALL PRODUCTS WITH PAGINATION
    // Example:
    // /api/products?page=0&size=10&sort=id,desc
    @GetMapping("/products")
    public ResponseEntity<Page<ProductSumaryResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        ProductResponse response = productService.findByProductId(id);
        return ResponseEntity.ok(response);
    }

    // PRODUCTS BY BRAND
    @GetMapping("/product/brand/{brandId}")
    public ResponseEntity<Page<ProductSumaryResponse>> getByBrand(
            @PathVariable Long brandId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                productService.getProductsByBrand(brandId, pageable)
        );
    }

    // PRODUCTS BY CATEGORY
    @GetMapping("/product/category/{categoryId}")
    public ResponseEntity<Page<ProductSumaryResponse>> getByCategory(
            @PathVariable Long categoryId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                productService.getProductsByCategory(categoryId, pageable)
        );
    }

    // PRODUCTS BY SUPPLIER
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<Page<ProductSumaryResponse>> getBySupplier(
            @PathVariable Long supplierId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                productService.getProductsBySupplier(supplierId, pageable)
        );
    }

    // ============================
    // FILTER PRODUCTS
    // Example:
    // /api/filter?brandId=1&categoryId=2&supplierId=3&page=0&size=10
    // ============================
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductSumaryResponse>> filterProducts(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long supplierId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                productService.filterProducts(
                        brandId,
                        categoryId,
                        supplierId,
                        pageable
                )
        );
    }

    // ============================
    // BRANDS
    // ============================

    @GetMapping("/brands")
    public ResponseEntity<List<BrandResponse>> getAllBrands() {
        return ResponseEntity.ok(
            productService.getAllBrands()
        );
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<BrandResponse> getBrandDetail(@PathVariable Long id) {
        return ResponseEntity.ok(
                productService.getBrandDetailById(id)
        );
    }

    // ============================
    // SUPPLIERS
    // ============================

    @GetMapping("/suppliers")
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers() {
        return ResponseEntity.ok(
            productService.getAllSuppliers()
        );
    }

    @GetMapping("/suppliers/{id}")
    public ResponseEntity<SupplierDetailResponse> getSupplierDetail(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                productService.getSupplierDetailById(id)
        );
    }

    // ============================
    // CATEGORIES
    // ============================

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(
            productService.getAllCategories()
        );
    }
}