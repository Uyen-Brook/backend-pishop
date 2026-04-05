package com.backend.pishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.pishop.entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    @Query("""
        SELECT p FROM Promotion p
        JOIN p.productPromotions pp
        WHERE pp.product.id = :productId
        AND p.isActive = true
        AND CURRENT_DATE BETWEEN p.startDate AND p.endDate
    """)
    List<Promotion> findActivePromotionsByProductId(Long productId);
}