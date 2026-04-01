package com.backend.pishop.controller;

import com.backend.pishop.response.CartResponse;
import com.backend.pishop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Lấy thông tin giỏ hàng
    @GetMapping("/{accountId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long accountId) {
        CartResponse cart = cartService.getCart(accountId);
        return ResponseEntity.ok(cart);
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/{accountId}/add")
    public ResponseEntity<String> addToCart(
            @PathVariable Long accountId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        cartService.addToCart(accountId, productId, quantity);
        return ResponseEntity.ok("Product added to cart successfully");
    }

    // Cập nhật số lượng sản phẩm trong giỏ
    @PutMapping("/{accountId}/update")
    public ResponseEntity<String> updateQuantity(
            @PathVariable Long accountId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        cartService.updateCartItem(accountId, productId, quantity);
        return ResponseEntity.ok("Quantity updated successfully");
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/{accountId}/remove/{productId}")
    public ResponseEntity<String> removeItem(
            @PathVariable Long accountId,
            @PathVariable Long productId) {
        cartService.removeItem(accountId, productId);
        return ResponseEntity.ok("Item removed from cart successfully");
    }
}
