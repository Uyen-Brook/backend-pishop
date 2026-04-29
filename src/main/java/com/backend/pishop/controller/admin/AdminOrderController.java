package com.backend.pishop.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.backend.pishop.service.admin.AdminOrderService;
import com.backend.pishop.entity.Order;
import com.backend.pishop.enums.OrderStatus;
import com.backend.pishop.config.APIResponse;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService orderService;

    @GetMapping
    public APIResponse<List<Order>> listAll() {
        return APIResponse.success(orderService.listAll());
    }

    @GetMapping("/by-status")
    public APIResponse<List<Order>> byStatus(@RequestParam OrderStatus status) {
        return APIResponse.success(orderService.listByStatus(status));
    }

    @PatchMapping("/{id}/status")
    public APIResponse<Order> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return APIResponse.success(orderService.updateStatus(id, status));
    }

    @PostMapping("/{id}/cancel")
    public APIResponse<Order> cancel(@PathVariable Long id) {
        return APIResponse.success(orderService.cancelOrder(id));
    }
}
