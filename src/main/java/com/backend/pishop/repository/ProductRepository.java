package com.backend.pishop.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import org.springframework.data.repository.query.Param;
import com.backend.pishop.entity.Product;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

    @Override
    @EntityGraph(attributePaths = {
            "brand",
            "supplier",
            "category",
            "productPromotions",
            "productPromotions.promotion"
    })
    Page<Product> findAll(Pageable pageable);

    // ============================
    // BRAND
    // ============================
    @EntityGraph(attributePaths = {
            "brand",
            "supplier",
            "category",
            "productPromotions",
            "productPromotions.promotion"
    })
    Page<Product> findByBrand_Id(
            Long brandId,
            Pageable pageable
    );

    // ============================
    // CATEGORY
    // ============================
    @EntityGraph(attributePaths = {
            "brand",
            "supplier",
            "category",
            "productPromotions",
            "productPromotions.promotion"
    })
    Page<Product> findByCategory_Id(
            Long categoryId,
            Pageable pageable
    );

    // ============================
    // SUPPLIER
    // ============================
    @EntityGraph(attributePaths = {
            "brand",
            "supplier",
            "category",
            "productPromotions",
            "productPromotions.promotion"
    })
    Page<Product> findBySupplier_Id(
            Long supplierId,
            Pageable pageable
    );

    // ============================
    // COMBO FILTER
    // ============================
    @EntityGraph(attributePaths = {
            "brand",
            "supplier",
            "category",
            "productPromotions",
            "productPromotions.promotion"
    })
    Page<Product> findByBrand_IdAndCategory_IdAndSupplier_Id(
            Long brandId,
            Long categoryId,
            Long supplierId,
            Pageable pageable
    );

    // ============================
    // SEARCH
    // ============================
    @Query("""
            SELECT p
            FROM Product p
            WHERE
                (:keyword IS NULL
                    OR LOWER(p.modelName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(p.modelNumber) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND (:categoryId IS NULL OR p.category.id = :categoryId)
            AND (:brandId IS NULL OR p.brand.id = :brandId)
            AND (:supplierId IS NULL OR p.supplier.id = :supplierId)
            AND (:deleted IS NULL OR p.deleted = :deleted)
        """)
    Page<Product> searchProducts(
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            @Param("brandId") Long brandId,
            @Param("supplierId") Long supplierId,
            @Param("deleted") Boolean deleted,
            Pageable pageable
    );
}