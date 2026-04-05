package com.backend.pishop.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // lấy tất cả
    @GetMapping("/products")
    public ResponseEntity<List<ProductSumaryResponse>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/product/brand/{brandId}")
    public ResponseEntity<List<ProductSumaryResponse>> getByBrand(@PathVariable Long brandId) {
        return ResponseEntity.ok(productService.getProductsByBrand(brandId));
    }

    @GetMapping("/product/category/{categoryId}")
    public ResponseEntity<List<ProductSumaryResponse>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    // ============================
    // FILTER BY SUPPLIER
    // ============================
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<ProductSumaryResponse>> getBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(productService.getProductsBySupplier(supplierId));
    }

    // ============================
    // COMBO FILTER
    // /api/products/filter?brandId=1&categoryId=2&supplierId=3
    // ============================
    @GetMapping("/filter")
    public ResponseEntity<List<ProductSumaryResponse>> filterProducts(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long supplierId
    ) {
        return ResponseEntity.ok(productService.filterProducts(brandId, categoryId, supplierId));
    }
   
    
	  @GetMapping("/brands")
	  public List<BrandRespone> getAllBrands() {
	      return productService.getAllBrands();
	  }
	 
	  
	  @GetMapping("/brands/{id}")
	  public BrandRespone getBrandDetail(@PathVariable Long id) {
	      return productService.getBrandDetailById(id);
	  }
	  
	  @GetMapping("/suppliers")
    public List<SupplierResponse> getAllSuppliers() {
        return productService.getAllSuppliers();
    }

    @GetMapping("/suppliers/{id}")
    public SupplierDetailResponse getSupplierDetail(@PathVariable Long id) {
        return productService.getSupplierDetailById(id);
    }
    
    
    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories() {
		return productService.getAllCategories();
	}
    
   

}