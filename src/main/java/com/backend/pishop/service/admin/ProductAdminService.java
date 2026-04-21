package com.backend.pishop.service.admin;

import com.backend.pishop.entity.Product;
import com.backend.pishop.repository.ProductRepository;
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

    @Transactional
    public Product createProduct(ProductCreateRequest request) {
        Product product = new Product();
        // TODO: Map fields from request to product
        // ...
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, ProductUpdateRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) throw new RuntimeException("Product not found");
        Product product = optionalProduct.get();
        // TODO: Map updated fields from request to product
        // ...
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