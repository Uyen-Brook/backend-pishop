package com.backend.pishop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.pishop.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
    @EntityGraph(attributePaths = {
            "items",
            "items.product",
            "items.product.productPromotions",
            "items.product.productPromotions.promotion"
    })
    Optional<Cart> findByAccountId(Long accountId);
}
