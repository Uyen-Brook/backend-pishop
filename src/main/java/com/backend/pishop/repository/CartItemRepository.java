package com.backend.pishop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.pishop.entity.CartItem;
import com.backend.pishop.response.CartItemResponse;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	Optional<CartItem> findByCart_IdAndProduct_Id(Long cartId, Long productId);
}
