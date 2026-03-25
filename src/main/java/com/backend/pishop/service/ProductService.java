package com.backend.pishop.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.pishop.entity.Brand;
import com.backend.pishop.entity.Category;
import com.backend.pishop.entity.Product;
import com.backend.pishop.entity.Supplier;
import com.backend.pishop.mapper.BrandMapper;
import com.backend.pishop.mapper.CategoryDetailMapper;
import com.backend.pishop.mapper.CategoryMapper;
import com.backend.pishop.mapper.ProductMapper;
import com.backend.pishop.mapper.SupplierMapper;
import com.backend.pishop.repository.BrandRepository;
import com.backend.pishop.repository.CategoryRepository;
import com.backend.pishop.repository.ProductRepository;
import com.backend.pishop.repository.SupplierRepository;
import com.backend.pishop.response.BrandRespone;
import com.backend.pishop.response.CategoryDetailResponse;
import com.backend.pishop.response.CategoryResponse;
import com.backend.pishop.response.ProductResponse;
import com.backend.pishop.response.SupplierResponse;

import lombok.RequiredArgsConstructor;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;

    // ================= PRODUCT =================

    // get all product (sort desc)
    public List<ProductResponse> getAllProducts(String sortBy) {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, sortBy))
        		.stream()
        		.map(ProductMapper::toResponse)
        		.toList();
    }

    // search product
    public List<ProductResponse> searchProducts(
            String name,
            String brand,
            String supplier,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String status
    ) {
        return productRepository.findAll(
                (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    if (name != null) {
                        predicates.add(cb.like(cb.lower(root.get("modelName")), "%" + name.toLowerCase() + "%"));
                    }

                    if (brand != null) {
                        predicates.add(cb.like(cb.lower(root.join("brand").get("brandName")), "%" + brand.toLowerCase() + "%"));
                    }

                    if (supplier != null) {
                        predicates.add(cb.like(cb.lower(root.join("supplier").get("name")), "%" + supplier.toLowerCase() + "%"));
                    }

                    if (minPrice != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("basePrice"), minPrice));
                    }

                    if (maxPrice != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("basePrice"), maxPrice));
                    }

                    if (status != null) {
                        predicates.add(cb.equal(root.get("ProductStatus"), status));
                    }

                    return cb.and(predicates.toArray(new Predicate[0]));
                }
        ).stream().map(ProductMapper::toResponse).toList();
        		
    }

    public List<ProductResponse> getProductsByBrand(Integer brandId) {
        return productRepository.findByBrandId(brandId, Sort.by(Sort.Direction.DESC, "createAt"))
        		.stream().map(ProductMapper::toResponse).toList();
    }

    public List<ProductResponse> getProductsBySupplier(Integer supplierId) {
        return productRepository.findBySupplierId(supplierId, Sort.by(Sort.Direction.DESC, "createAt"))
        		.stream().map(ProductMapper::toResponse).toList();
    }

    public List<ProductResponse> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId, Sort.by(Sort.Direction.DESC, "createAt"))
        		.stream().map(ProductMapper::toResponse).toList();
    }

    // ================= BRAND =================

    public List<BrandRespone> getAllBrands() {
        return brandRepository.findAllByOrderByNameAsc()
        		.stream().map(BrandMapper::toResponse).toList();
    }

    public Brand getBrandDetail(Integer id) {
        return brandRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Brand not found"));
    }

    // ================= SUPPLIER =================

    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAllByOrderByNameDesc()
        		.stream().map(SupplierMapper::toResponse).toList();
    }

    public Supplier getSupplierDetail(Long id) {
        return supplierRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Supplier not found"));
    }

    public List<Supplier> searchSuppliers(
            String name,
            String email,
            String taxcode,
            String phone,
            String note
    ) {
        return supplierRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null)
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));

            if (email != null)
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));

            if (taxcode != null)
                predicates.add(cb.like(root.get("taxcode"), "%" + taxcode + "%"));

            if (phone != null)
                predicates.add(cb.like(root.get("phone"), "%" + phone + "%"));

            if (note != null)
                predicates.add(cb.like(cb.lower(root.get("note")), "%" + note.toLowerCase() + "%"));

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    // ================= CATEGORY =================

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAllByOrderByNameAsc()
                .stream()
                .map(CategoryMapper::toResponse) // giả sử toResponse(Category c)
                .toList();
    }

    public CategoryDetailResponse getCategoryDetail(Integer id) {
        return categoryRepository.findById(id)
                .map(CategoryDetailMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

}