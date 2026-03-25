package com.backend.pishop.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.pishop.entity.Brand;
import com.backend.pishop.entity.Category;
import com.backend.pishop.entity.Product;
import com.backend.pishop.entity.Supplier;
import com.backend.pishop.response.BrandRespone;
import com.backend.pishop.response.CategoryDetailResponse;
import com.backend.pishop.response.CategoryResponse;
import com.backend.pishop.response.ProductResponse;
import com.backend.pishop.response.SupplierResponse;
import com.backend.pishop.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ================= PRODUCT =================

    @GetMapping("/products")
    public List<ProductResponse> getAllProducts(
            @RequestParam(defaultValue = "createAt") String sortBy
    ) {
        return productService.getAllProducts(sortBy);
    }

    @GetMapping("/products/search")
    public List<ProductResponse> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String supplier,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String status
    ) {
        return productService.searchProducts(name, brand, supplier, minPrice, maxPrice, status);
    }

    @GetMapping("/products/brand/{id}")
    public List<ProductResponse> getByBrand(@PathVariable Integer id) {
        return productService.getProductsByBrand(id);
    }

    @GetMapping("/products/supplier/{id}")
    public List<ProductResponse> getBySupplier(@PathVariable Integer id) {
        return productService.getProductsBySupplier(id);
    }

    @GetMapping("/products/category/{id}")
    public List<ProductResponse> getByCategory(@PathVariable Integer id) {
        return productService.getProductsByCategory(id);
    }

    // ================= BRAND =================

    @GetMapping("/brands")
    public List<BrandRespone> getAllBrands() {
        return productService.getAllBrands();
    }

    @GetMapping("/brands/{id}")
    public Brand getBrandDetail(@PathVariable Integer id) {
        return productService.getBrandDetail(id);
    }

    // ================= SUPPLIER =================

    @GetMapping("/suppliers")
    public List<SupplierResponse> getAllSuppliers() {
        return productService.getAllSuppliers();
    }

    @GetMapping("/suppliers/{id}")
    public Supplier getSupplierDetail(@PathVariable Long id) {
        return productService.getSupplierDetail(id);
    }

    @GetMapping("/suppliers/search")
    public List<Supplier> searchSuppliers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String taxcode,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String note
    ) {
        return productService.searchSuppliers(name, email, taxcode, phone, note);
    }

    // ================= CATEGORY =================

    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories() {
        return productService.getAllCategories();
    }
    
    @GetMapping("/categories/{id}")
    public CategoryDetailResponse getCategoryDetail(@PathVariable Integer id) {
		return productService.getCategoryDetail(id);
	}
}