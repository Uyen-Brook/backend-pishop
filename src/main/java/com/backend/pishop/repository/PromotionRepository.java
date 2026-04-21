package com.backend.pishop.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.pishop.entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

//    @Query("""
//        SELECT p FROM Promotion p
//        JOIN p.productPromotions pp
//        WHERE pp.product.id = :productId
//        AND p.isActive = true
//        AND CURRENT_DATE BETWEEN p.startDate AND p.endDate
//    """)
//    List<Promotion> findActivePromotionsByProductId(Long productId);
    
    @Query(value = """
            SELECT p.*
            FROM promotions p
            JOIN product_promotions pp ON p.id = pp.promotion_id
            WHERE pp.product_id = :productId
              AND p.is_active = true
              AND p.start_date <= CURRENT_DATE
              AND p.end_date >= CURRENT_DATE
            ORDER BY 
                CASE 
                    WHEN p.discount_type = 'PERCENT' 
                        THEN p.discount_value * :price / 100
                    ELSE p.discount_value
                END DESC
            LIMIT 1
        """, nativeQuery = true)
        Promotion findBestPromotion(
                @Param("productId") Long productId,
                @Param("price") BigDecimal price
        );
}