package com.backend.pishop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.backend.pishop.service.OrderService;
import com.backend.pishop.entity.Order;
import com.backend.pishop.enums.OrderStatus;
import com.backend.pishop.request.OrderRequest;
import com.backend.pishop.response.OrderResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/orders")
public class OrderController {
    private final OrderService orderService;
    
    @PostMapping("/create")
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
		return orderService.createOrder(orderRequest);
	}

    // 1. Lấy tất cả đơn hàng theo accountId (sort by date desc)
    @GetMapping("/by-account/{accountId}")
    public List<OrderResponse> getOrdersByAccount(@PathVariable Long accountId) {
        return orderService.getOrdersByAccountId(accountId);
    }

    // 2. Lấy đơn hàng theo orderStatus
    @GetMapping("/by-status")
    public List<OrderResponse> getOrdersByStatus(@RequestParam OrderStatus status) {
        return orderService.getOrdersByStatus(status);
    }
 
    // 3. Lấy tất cả đơn hàng chưa thanh toán của accountId (BANK, UNPAID)
    @GetMapping("/unpaid-bank/{accountId}")
    public List<OrderResponse> getUnpaidBankOrders(@PathVariable Long accountId) {
        return orderService.getUnpaidBankOrdersByAccountId(accountId);
    }

    // 4. Yêu cầu hủy đơn hàng
    @PostMapping("/cancel")
    public boolean cancelOrder(@RequestBody CancelOrderRequest req) {
        return orderService.requestCancelOrder(req.getOrderId(), req.getAccountId());
    }

    @Data
    public static class CancelOrderRequest {
        private Long orderId;
        private Long accountId;
    }
}