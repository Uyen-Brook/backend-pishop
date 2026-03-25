package com.backend.pishop.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.backend.pishop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    // Lấy theo brand
    List<Product> findByBrandId(Integer brandId, Sort sort);

    // Lấy theo supplier
    List<Product> findBySupplierId(Integer supplierId, Sort sort);

    // Lấy theo category
    List<Product> findByCategoryId(Integer categoryId, Sort sort);
}