package com.backend.pishop.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.pishop.entity.Order;
import com.backend.pishop.enums.OrderStatus;
import com.backend.pishop.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminOrderService {

    private final OrderRepository orderRepository;

    public List<Order> listAll() {
        return orderRepository.findAll();
    }

    public List<Order> listByStatus(OrderStatus status) {
        return orderRepository.findByOrderStatusOrderByCreateAtDesc(status);
    }

    public Order updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    public Order cancelOrder(Long orderId) {
        return updateStatus(orderId, OrderStatus.CANCELLED);
    }
}
