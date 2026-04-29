package com.backend.pishop.service.admin;

import com.backend.pishop.entity.Product;
import com.backend.pishop.entity.Brand;
import com.backend.pishop.entity.Supplier;
import com.backend.pishop.entity.Category;
import com.backend.pishop.repository.ProductRepository;
import com.backend.pishop.repository.BrandRepository;
import com.backend.pishop.repository.SupplierRepository;
import com.backend.pishop.repository.CategoryRepository;
import com.backend.pishop.request.ProductCreateRequest;
import com.backend.pishop.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductAdminService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final BrandRepository brandRepository;
    @Autowired
    private final SupplierRepository supplierRepository;
    @Autowired
    private final CategoryRepository categoryRepository;


        @Transactional
        public Product createProduct(ProductCreateRequest request) {
        Product product = new Product();
        // Map fields from request to product
        product.setModelName(request.getModelName());
        product.setSpecification(request.getSpecification());
        product.setThumbnail(request.getThumbnail());
        product.setDescription(request.getDescription());
        product.setImportPrice(request.getImportPrice());
        product.setTaxVat(request.getTaxVat());
        product.setPrice(request.getPrice());
        product.setModelNumber(request.getModelNumber());
        product.setListImage(request.getListImage());
        product.setQuanlity(request.getQuanlity());
        product.setProductStatus(request.getProductStatus());
        product.setDeleted(false);
        product.setCreateAt(java.time.LocalDateTime.now());
        product.setUpdateAt(java.time.LocalDateTime.now());

        // Set Brand
        Brand brand = brandRepository.findById(request.getBrandId())
            .orElseThrow(() -> new RuntimeException("Brand not found"));
        product.setBrand(brand);

        // Set Supplier
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
            .orElseThrow(() -> new RuntimeException("Supplier not found"));
        product.setSupplier(supplier);

        // Set Category
        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        return productRepository.save(product);
        }

    @Transactional
    public Product updateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Map các field từ request sang product
        product.setModelName(request.getModelName());
        product.setSpecification(request.getSpecification());
        product.setThumbnail(request.getThumbnail());
        product.setDescription(request.getDescription());
        product.setImportPrice(request.getImportPrice());
        product.setTaxVat(request.getTaxVat());
        product.setPrice(request.getPrice());
        product.setModelNumber(request.getModelNumber());
        product.setListImage(request.getListImage());
        product.setQuanlity(request.getQuanlity());
        product.setProductStatus(request.getProductStatus());

        // Nếu có quan hệ ManyToOne thì cần load entity từ repository
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrand(brand);
        }
        if (request.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            product.setSupplier(supplier);
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        // Cập nhật thời gian
        return productRepository.save(product);
    }

    @Transactional
    public void softDeleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Transactional
    public void restoreProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setDeleted(false);
        productRepository.save(product);
    }

    @Transactional
    public void hardDeleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
}