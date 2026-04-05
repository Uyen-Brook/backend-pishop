package com.backend.pishop.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.pishop.entity.Brand;
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
import com.backend.pishop.response.BrandRespone;
import com.backend.pishop.response.CategoryResponse;
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
    // LẤY TẤT CẢ SẢN PHẨM (FULL GRAPH)
    // ============================
    public List<ProductSumaryResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::toSummaryResponse)
                .toList();
    }

    // ============================
    // LỌC THEO BRAND
    // ============================
    public List<ProductSumaryResponse> getProductsByBrand(Long brandId) {
        List<Product> products = productRepository.findByBrand_Id(brandId);
        return products.stream()
                .map(ProductMapper::toSummaryResponse)
                .toList();
    }

    // ============================
    // LỌC THEO CATEGORY
    // ============================
    public List<ProductSumaryResponse> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategory_Id(categoryId);
        return products.stream()
                .map(ProductMapper::toSummaryResponse)
                .toList();
    }

    // ============================
    // LỌC THEO SUPPLIER
    // ============================
    public List<ProductSumaryResponse> getProductsBySupplier(Long supplierId) {
        List<Product> products = productRepository.findBySupplier_Id(supplierId);
        return products.stream()
                .map(ProductMapper::toSummaryResponse)
                .toList();
    }

    // ============================
    // COMBO FILTER (brand + category + supplier)
    // ============================
    public List<ProductSumaryResponse> filterProducts(Long brandId, Long categoryId, Long supplierId) {

        // Nếu cả 3 đều null
        if (brandId == null && categoryId == null && supplierId == null) {
            return getAllProducts().stream()
                    .map(p -> ProductMapper.toSummaryResponse(
                            productRepository.findById(p.getId()).orElse(null)
                    ))
                    .toList();
        }

        // dùng combo query
        if (brandId != null && categoryId != null && supplierId != null) {
            List<Product> products = productRepository
                    .findByBrand_IdAndCategory_IdAndSupplier_Id(brandId, categoryId, supplierId);

            return products.stream()
                    .map(ProductMapper::toSummaryResponse)
                    .toList();
        }

        // brand
        if (brandId != null && categoryId == null && supplierId == null) {
            return getProductsByBrand(brandId);
        }

        // Nếu  category
        if (categoryId != null && brandId == null && supplierId == null) {
            return getProductsByCategory(categoryId);
        }

        // Nếu supplier
        if (supplierId != null && brandId == null && categoryId == null) {
            return getProductsBySupplier(supplierId);
        }

        // Nhưng tạm thời mình dùng filter thủ công
        List<Product> all = productRepository.findAll();

        return all.stream()
                .filter(p -> brandId == null || p.getBrand().getId().equals(brandId))
                .filter(p -> categoryId == null || p.getCategory().getId().equals(categoryId))
                .filter(p -> supplierId == null || p.getSupplier().getId().equals(supplierId))
                .map(ProductMapper::toSummaryResponse)
                .toList();
    }

    
    public List<CategoryResponse> getAllCategories() {
    	return categoryRepository.findAll().stream()
				.map(CategoryMapper::toResponse)
				.toList();
	}
    
    public List<BrandRespone> getAllBrands() {
    	return brandRepository.findAll().stream()
    			.map(BrandMapper::toResponse)
    			.toList();
    }
    
    public List<SupplierResponse> getAllSuppliers() {
		return supplierRepository.findAll().stream()
				.map(SupplierMapper::toResponse)
				.toList();
	}
    
    
    public BrandRespone getBrandDetailById(Long id) {
        return brandRepository.findById(id)
                .map(BrandMapper::toResponse)
                .orElseThrow();
    }
    
    public SupplierDetailResponse getSupplierDetailById(Long id) {
		return supplierRepository.findById(id)
				.map(SupplierMapper::toResponseDetail)
				.orElseThrow();
	}

}