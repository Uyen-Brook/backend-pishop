package com.backend.pishop.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.pishop.entity.ProductPromotion;

public interface ProductPromotionRepository extends JpaRepository<ProductPromotion, Long>{
	
	@Query("select pp from ProductPromotion pp join fetch pp.promotion p where pp.product.id = :productId " +
	       "and p.isActive = true and p.startDate <= :today and p.endDate >= :today")
	Optional<ProductPromotion> findActiveByProductId(@Param("productId") Long productId, @Param("today") LocalDate today);
}