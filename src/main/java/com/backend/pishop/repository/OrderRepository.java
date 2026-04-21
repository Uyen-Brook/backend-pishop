package com.backend.pishop.repository;

import java.util.List;

import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.pishop.entity.Order;
import com.backend.pishop.enums.OrderStatus;
import com.backend.pishop.enums.PayStatus;

public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByAccountIdOrderByCreateAtDesc(Long accountId);
    List<Order> findByOrderStatusOrderByCreateAtDesc(OrderStatus status);
    List<Order> findByAccountIdAndPaymentMethodAndPayStatusOrderByCreateAtDesc(Long accountId, String paymentMethod, PayStatus payStatus);
}