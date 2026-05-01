package com.backend.pishop.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.pishop.enums.OrderStatus;
import com.backend.pishop.request.OrderRequest;
import com.backend.pishop.response.OrderResponse;
import com.backend.pishop.service.admin.AdminOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    // =========================
    // GET ALL (PAGINATION - Pageable)
    // =========================
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(
                adminOrderService.getAllOrders(pageable)
        );
    }

    // =========================
    // GET DETAIL
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderDetail(@PathVariable Long id) {
        return ResponseEntity.ok(adminOrderService.getOrderDetail(id));
    }

    // =========================
    // SEARCH (giữ list nếu chưa cần paging)
    // =========================
    @GetMapping("/search")
    public ResponseEntity<java.util.List<OrderResponse>> searchOrders(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) Long accountId
    ) {
        return ResponseEntity.ok(
                adminOrderService.searchOrders(keyword, status, accountId)
        );
    }

    // =========================
    // CREATE ORDER
    // =========================
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(adminOrderService.createOrder(request));
    }

    // =========================
    // UPDATE ORDER
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable Long id,
            @RequestBody OrderRequest request
    ) {
        return ResponseEntity.ok(
                adminOrderService.updateOrder(id, request)
        );
    }

    // =========================
    // CHANGE STATUS
    // =========================
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> changeStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {
        return ResponseEntity.ok(
                adminOrderService.updateStatus(id, status)
        );
    }

    // =========================
    // CANCEL ORDER
    // =========================
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(adminOrderService.cancelOrder(id));
    }

    // =========================
    // DELETE ORDER
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        adminOrderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}