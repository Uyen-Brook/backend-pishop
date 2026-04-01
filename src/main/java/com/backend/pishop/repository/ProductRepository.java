package com.backend.pishop.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.pishop.entity.Product;
import com.backend.pishop.response.ProductSumaryResponse;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	@EntityGraph(attributePaths = {
	        "brand",
	        "supplier",
	        "category",
	        "productPromotions",
	        "productPromotions.promotion"
	    })
	    List<Product> findAll();

	    @EntityGraph(attributePaths = {
	        "brand",
	        "supplier",
	        "category",
	        "productPromotions",
	        "productPromotions.promotion"
	    })
	    List<Product> findByBrand_Id(Long brandId);

	    @EntityGraph(attributePaths = {
	        "brand",
	        "supplier",
	        "category",
	        "productPromotions",
	        "productPromotions.promotion"
	    })
	    List<Product> findByCategory_Id(Long categoryId);

	    @EntityGraph(attributePaths = {
	        "brand",
	        "supplier",
	        "category",
	        "productPromotions",
	        "productPromotions.promotion"
	    })
	    List<Product> findBySupplier_Id(Long supplierId);

	    // 🔥 combo filter
	    @EntityGraph(attributePaths = {
	        "brand",
	        "supplier",
	        "category",
	        "productPromotions",
	        "productPromotions.promotion"
	    })
	    List<Product> findByBrand_IdAndCategory_IdAndSupplier_Id(
	            Long brandId, Long categoryId, Long supplierId);


}