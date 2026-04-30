package com.backend.pishop.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.pishop.entity.Order;
import com.backend.pishop.enums.OrderStatus;
import com.backend.pishop.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import com.backend.pishop.mapper.OrderMapper;

import com.backend.pishop.request.OrderRequest;
import com.backend.pishop.response.OrderResponse;



@Service
@RequiredArgsConstructor
@Transactional
public class AdminOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    // =====================================================
    // GET ALL
    // =====================================================

    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    // =====================================================
    // GET DETAIL
    // =====================================================

    public OrderResponse getOrderDetail(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Order not found"));

        return orderMapper.toResponse(order);
    }

    // =====================================================
    // SEARCH
    // =====================================================

    public List<OrderResponse> searchOrders(
            String keyword,
            OrderStatus status,
            Long accountId
    ) {

        return orderRepository.searchOrders(
                keyword,
                status,
                accountId
        ).stream()
         .map(orderMapper::toResponse)
         .toList();
    }

    // =====================================================
    // CREATE
    // =====================================================

    public OrderResponse createOrder(OrderRequest request) {

        Order order = new Order();

        order.setToName(request.getToName());
        order.setToPhone(request.getToPhone());
        order.setToAddress(request.getToAddress());

        order.setVoucherCode(request.getVoucherCode());

        order.setPaymentMethod(request.getPaymentMethod());

        order.setOrderStatus(OrderStatus.PENDDING);

        order.setCreateAt(LocalDateTime.now());
        order.setLastUpdate(LocalDateTime.now());

        Order saved = orderRepository.save(order);

        return orderMapper.toResponse(saved);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public OrderResponse updateOrder(
            Long id,
            OrderRequest request
    ) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Order not found"));

        order.setToName(request.getToName());
        order.setToPhone(request.getToPhone());
        order.setToAddress(request.getToAddress());

        order.setVoucherCode(request.getVoucherCode());

        order.setPaymentMethod(request.getPaymentMethod());

        order.setLastUpdate(LocalDateTime.now());

        Order updated = orderRepository.save(order);

        return orderMapper.toResponse(updated);
    }

    // =====================================================
    // CHANGE STATUS
    // =====================================================

    public OrderResponse updateStatus(
            Long orderId,
            OrderStatus status
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Order not found"));

        order.setOrderStatus(status);

        order.setLastUpdate(LocalDateTime.now());

        Order updated = orderRepository.save(order);

        return orderMapper.toResponse(updated);
    }

    // =====================================================
    // CANCEL ORDER
    // =====================================================

    public OrderResponse cancelOrder(Long orderId) {

        return updateStatus(orderId, OrderStatus.CANCELLED);
    }

    // =====================================================
    // DELETE
    // =====================================================

    public void deleteOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Order not found"));

        orderRepository.delete(order);
    }
    
}
