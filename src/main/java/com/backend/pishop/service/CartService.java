package com.backend.pishop.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.pishop.entity.Account;
import com.backend.pishop.entity.Cart;
import com.backend.pishop.entity.CartItem;
import com.backend.pishop.entity.Product;
import com.backend.pishop.mapper.CartMapper;
import com.backend.pishop.repository.AccountRepository;
import com.backend.pishop.repository.CartItemRepository;
import com.backend.pishop.repository.CartRepository;
import com.backend.pishop.repository.ProductRepository;
import com.backend.pishop.response.CartItemResponse;
import com.backend.pishop.response.CartResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final AccountRepository accountRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
  

    // ===== GET CART =====
    public CartResponse getCart(Long accountId) {

        Cart cart = cartRepository.findByAccountId(accountId)
                .orElse(null);

        if (cart == null || cart.getItems().isEmpty()) {
            CartResponse response = new CartResponse();
            response.setCartId(null);
            response.setItems(Collections.emptyList());
            response.setTotalPrice(BigDecimal.ZERO);
            return response;
        }

        List<CartItemResponse> items = cart.getItems().stream()
                .map(CartMapper::toResponse)
                .toList();

        BigDecimal total = items.stream()
                .map(i -> i.getFinalPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());
        response.setItems(items);
        response.setTotalPrice(total);

        return response;
    }
    
    // ===== ADD TO CART =====
    public void addToCart(Long accountId, Long productId, int quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be > 0");
        }

        // 1. Lấy hoặc tạo cart
        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    Account account = accountRepository.findById(accountId)
                            .orElseThrow(() -> new RuntimeException("Account not found"));
                    newCart.setAccount(account);
                    return cartRepository.save(newCart);
                });

        // 2. Lấy product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ⚠️ Check tồn kho
        if (product.getQuanlity() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        // 3. Tìm item trong cart (dùng luôn Set items thay vì query lại DB)
        Optional<CartItem> optionalItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (optionalItem.isPresent()) {
            CartItem item = optionalItem.get();

            int newQuantity = item.getQuantity() + quantity;

            if (product.getQuanlity() < newQuantity) {
                throw new RuntimeException("Exceed stock");
            }

            item.setQuantity(newQuantity);

        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);

            cart.getItems().add(newItem); // 🔥 quan trọng (cascade ALL)
        }

        cartRepository.save(cart);
    }

    // ===== UPDATE QUANTITY =====
    public void updateCartItem(Long accountId, Long productId, int quantity) {

        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (quantity <= 0) {
            cart.getItems().remove(item); // 🔥 orphanRemoval = true → auto delete
            return;
        }

        Product product = item.getProduct();

        if (product.getQuanlity() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        item.setQuantity(quantity);
        cartRepository.save(cart);
    }

    // ===== REMOVE ITEM =====
   
    public void removeItem(Long accountId, Long productId) {

        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found"));

        cart.getItems().remove(item); 
        cartRepository.save(cart);// 🔥 auto delete nhờ orphanRemoval
    }
    
}
