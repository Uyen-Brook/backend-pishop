package com.backend.pishop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.pishop.entity.Product;
import com.backend.pishop.mapper.BrandMapper;
import com.backend.pishop.mapper.CategoryMapper;
import com.backend.pishop.mapper.ProductMapper;
import com.backend.pishop.mapper.SupplierMapper;
import com.backend.pishop.repository.BrandRepository;
import com.backend.pishop.repository.CategoryRepository;
import com.backend.pishop.repository.ProductPromotionRepository;
import com.backend.pishop.repository.ProductRepository;
import com.backend.pishop.repository.PromotionRepository;
import com.backend.pishop.repository.SupplierRepository;
import com.backend.pishop.response.BrandResponse;
import com.backend.pishop.response.CategoryResponse;
import com.backend.pishop.response.ProductResponse;
import com.backend.pishop.response.ProductSumaryResponse;
import com.backend.pishop.response.SupplierDetailResponse;
import com.backend.pishop.response.SupplierResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    private final PromotionRepository promotionService;
    private final ProductPromotionRepository productPromotionRepository;

    // ============================
    // GET ALL PRODUCTS
    // ============================
    public Page<ProductSumaryResponse> getAllProducts(Pageable pageable) {

        return productRepository.findAll(pageable)
                .map(ProductMapper::toSummaryResponse);
    }

    // ============================
    // FILTER BY BRAND
    // ============================
    public Page<ProductSumaryResponse> getProductsByBrand(
            Long brandId,
            Pageable pageable
    ) {

        return productRepository.findByBrand_Id(brandId, pageable)
                .map(ProductMapper::toSummaryResponse);
    }

    // ============================
    // FILTER BY CATEGORY
    // ============================
    public Page<ProductSumaryResponse> getProductsByCategory(
            Long categoryId,
            Pageable pageable
    ) {

        return productRepository.findByCategory_Id(categoryId, pageable)
                .map(ProductMapper::toSummaryResponse);
    }

    // ============================
    // FILTER BY SUPPLIER
    // ============================
    public Page<ProductSumaryResponse> getProductsBySupplier(
            Long supplierId,
            Pageable pageable
    ) {

        return productRepository.findBySupplier_Id(supplierId, pageable)
                .map(ProductMapper::toSummaryResponse);
    }

    // ============================
    // COMBO FILTER
    // ============================
    public Page<ProductSumaryResponse> filterProducts(
            Long brandId,
            Long categoryId,
            Long supplierId,
            Pageable pageable
    ) {

        // Không filter gì
        if (brandId == null && categoryId == null && supplierId == null) {
            return getAllProducts(pageable);
        }

        // Filter cả 3
        if (brandId != null && categoryId != null && supplierId != null) {

            return productRepository
                    .findByBrand_IdAndCategory_IdAndSupplier_Id(
                            brandId,
                            categoryId,
                            supplierId,
                            pageable
                    )
                    .map(ProductMapper::toSummaryResponse);
        }

        // Brand only
        if (brandId != null && categoryId == null && supplierId == null) {
            return getProductsByBrand(brandId, pageable);
        }

        // Category only
        if (categoryId != null && brandId == null && supplierId == null) {
            return getProductsByCategory(categoryId, pageable);
        }

        // Supplier only
        if (supplierId != null && brandId == null && categoryId == null) {
            return getProductsBySupplier(supplierId, pageable);
        }

        // Multi filter linh hoạt
        return productRepository.findAll(pageable)
                .map(ProductMapper::toSummaryResponse)
                .map(product -> product);
    }

    // ============================
    // CATEGORIES
    // ============================
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }
    // ============================
    // BRANDS
    // ============================
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(BrandMapper::toResponse)
                .toList();
    }
    // ============================
    // SUPPLIERS
    // ============================
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(SupplierMapper::toResponse)
                .toList();
    }

    // ============================
    // BRAND DETAIL
    // ============================
    public BrandResponse getBrandDetailById(Long id) {

        return brandRepository.findById(id)
                .map(BrandMapper::toResponse)
                .orElseThrow();
    }

    // ============================
    // SUPPLIER DETAIL
    // ============================
    public SupplierDetailResponse getSupplierDetailById(Long id) {

        return supplierRepository.findById(id)
                .map(SupplierMapper::toResponseDetail)
                .orElseThrow();
    }

    // ============================
    // PRODUCT DETAIL
    // ============================
    public ProductResponse findByProductId(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductMapper.toResponse(product);
    }
}