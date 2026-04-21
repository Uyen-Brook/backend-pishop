package com.backend.pishop.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import com.backend.pishop.entity.Account;
import com.backend.pishop.entity.AccountVoucher;
import com.backend.pishop.entity.Order;
import com.backend.pishop.entity.OrderItem;
import com.backend.pishop.entity.Product;
import com.backend.pishop.entity.ProductPromotion;
import com.backend.pishop.entity.Promotion;
import com.backend.pishop.entity.Province;
import com.backend.pishop.entity.Voucher;
import com.backend.pishop.entity.Ward;
import com.backend.pishop.enums.DiscountType;
import com.backend.pishop.enums.OrderStatus;
import com.backend.pishop.enums.PayStatus;
import com.backend.pishop.enums.VoucherStatus;
import com.backend.pishop.repository.AccountRepository;
import com.backend.pishop.repository.AccountVoucherRepository;
import com.backend.pishop.repository.OrderRepository;
import com.backend.pishop.repository.ProductPromotionRepository;
import com.backend.pishop.repository.ProductRepository;
import com.backend.pishop.repository.PromotionRepository;
import com.backend.pishop.repository.ProvinceRepository;
import com.backend.pishop.repository.VoucherRepository;
import com.backend.pishop.repository.WardRepository;
import com.backend.pishop.request.OrderItemRequest;
import com.backend.pishop.request.OrderRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final ProductRepository productRepository;
	private final AccountRepository accountRepository;
	private final VoucherRepository voucherRepository;
	private final ProductPromotionRepository productPromotionRepository;
	private final PromotionRepository promotionRepository;
	private final OrderRepository orderRepository;
	private final AccountVoucherRepository accountVoucherRepository;
	private final ProvinceRepository provinceRepository;
	private final WardRepository wardRepository;
	private final EmailService emailService;

	@Transactional
	public Order createOrder(OrderRequest request) {

		// Validate OrderRequest input
		if (request == null) {
			throw new IllegalArgumentException("Order request must not be null");
		}
		if (request.getAccountId() == null) {
			throw new IllegalArgumentException("Account ID is required");
		}
		if (request.getToName() == null || request.getToName().trim().isEmpty() || request.getToName().length() > 100) {
			throw new IllegalArgumentException("Recipient name is required and must be less than 100 characters");
		}
		if (request.getToPhone() == null || !request.getToPhone().matches("^(0|\\+84)[0-9]{9,10}$")) {
			throw new IllegalArgumentException("Invalid phone number format");
		}
		if (request.getToAddress() == null || request.getToAddress().trim().isEmpty() || request.getToAddress().length() > 255) {
			throw new IllegalArgumentException("Address is required and must be less than 255 characters");
		}
		if (request.getToProvinceCode() == null || request.getToProvinceCode().trim().isEmpty()) {
			throw new IllegalArgumentException("Province code is required");
		}
		if (request.getToWardCode() == null || request.getToWardCode().trim().isEmpty()) {
			throw new IllegalArgumentException("Ward code is required");
		}
		if (request.getPaymentMethod() == null) {
			throw new IllegalArgumentException("Payment method is required");
		}
		if (request.getItems() == null || request.getItems().isEmpty()) {
			throw new IllegalArgumentException("Order must contain at least one item");
		}
		// Validate each OrderItemRequest
		int idx = 0;
		for (OrderItemRequest item : request.getItems()) {
			if (item == null) {
				throw new IllegalArgumentException("Order item at index " + idx + " is null");
			}
			if (item.getProductId() == null) {
				throw new IllegalArgumentException("Product ID is required for item at index " + idx);
			}
			if (item.getQuantity() == null || item.getQuantity() <= 0) {
				throw new IllegalArgumentException("Quantity must be greater than 0 for item at index " + idx);
			}
			idx++;
		}

		// 1. Lấy account
		Account account = accountRepository.findById(request.getAccountId())
				.orElseThrow(() -> new RuntimeException("Account not found"));

		// 2. Tạo order
		Order order = new Order();
		order.setAccount(account);
		order.setToName(request.getToName());
		order.setToPhone(request.getToPhone());
		order.setToAddress(request.getToAddress());
		order.setPaymentMethod(request.getPaymentMethod());

		order.setOrderStatus(OrderStatus.PENDDING);
		order.setPayStatus(PayStatus.UNPAID);
		order.setCreateAt(LocalDateTime.now());

		// 3. Gán địa chỉ
		Province province = provinceRepository.findById(request.getToProvinceCode())
				.orElseThrow(() -> new RuntimeException("Province not found"));

		Ward ward = wardRepository.findById(request.getToWardCode())
				.orElseThrow(() -> new RuntimeException("Ward not found"));

		order.setProvince(province);
		order.setWard(ward);

		// 4. Build OrderItems + tính tổng tiền
		BigDecimal totalAmount = BigDecimal.ZERO;

		// kiểm tra số lượng item có lớn hơn số lượng còn lại không
		// gủi gmail,
		// xử lý voucher tìm trong accountvoucher = accid, vouchercode kết quả = voucher
		// tính giá và product
		// tính tổng đơn giá
		// tạo đơn

		List<OrderItem> orderItems = new ArrayList<>();

		for (OrderItemRequest itemReq : request.getItems()) {

			Product product = productRepository.findById(itemReq.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found"));

			// so sánh số lượng của order so với số luọng product hiện có
			if (product.getQuanlity() < itemReq.getQuantity()) {
				throw new RuntimeException("Sản phẩm không đủ hàng");
			}
			// tính giá sau khi áp dụng khuyến mãi
			BigDecimal price = product.getPrice();
			Promotion promotion = promotionRepository.findBestPromotion(product.getId(), price);

			BigDecimal discountPrice = price; // mặc định

			if (promotion != null) {
				if (promotion.getDiscountType() == DiscountType.PERCENT) {
					discountPrice = price
							.subtract(price.multiply(promotion.getDiscountValue()).divide(BigDecimal.valueOf(100)));
				} else {
					discountPrice = price.subtract(promotion.getDiscountValue());
				}

				// tránh âm
				if (discountPrice.compareTo(BigDecimal.ZERO) < 0) {
					discountPrice = BigDecimal.ZERO;
				}
			}

			OrderItem item = new OrderItem();
			item.setProduct(product);
			item.setQuantity(itemReq.getQuantity());
			item.setPrice(price);
			item.setDiscountPrice(discountPrice);
			item.setOrder(order);
			if(discountPrice.compareTo(product.getPrice()) < 0) {			
				item.setPromotionId(promotion.getId());
			}
			orderItems.add(item);
			totalAmount = totalAmount.add(discountPrice.multiply(BigDecimal.valueOf(itemReq.getQuantity())));
		}

		order.setItems(orderItems);
		order.setTotalAmount(totalAmount);

		// 5. Xử lý voucher tạm thời bỏ qua chương trình tích điểm
		 orderRepository.save(order);
		
		sendOrderConfirmationMail(order, account);
		// 8. Save
		return order;
	}



	private void sendOrderConfirmationMail(Order order, Account account) {

	    String itemsHtml = order.getItems().stream()
	            .map(item -> String.format("""
	                    <tr>
	                        <td>%s</td>
	                        <td>%d</td>
	                        <td>%s</td>
	                        <td>%s</td>
	                    </tr>
	                    """,
	                    item.getProduct().getModelName(),
	                    item.getQuantity(),
	                    formatPrice(item.getPrice()),
	                    formatPrice(item.getDiscountPrice())
	            ))
	            .collect(Collectors.joining());

	    String message = String.format("""
	        <div style="font-family: Arial, sans-serif; line-height: 1.6; color:#333">
	        
	            <h2 style="color:#F42525;">Xác nhận đặt hàng thành công!</h2>
	            
	            <p>Cảm ơn bạn đã mua sắm tại <b>PiShop</b>.</p>
	            
	            <h3>Thông tin đơn hàng</h3>
	            <p><b>Mã đơn hàng:</b> #%s</p>
	            <p><b>Ngày đặt:</b> %s</p>
	            
	            <table border="1" cellpadding="8" cellspacing="0" width="100%%" style="border-collapse: collapse;">
	                <thead style="background-color:#f5f5f5;">
	                    <tr>
	                        <th>Sản phẩm</th>
	                        <th>Số lượng</th>
	                        <th>Giá gốc</th>
	                        <th>Giá sau KM</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    %s
	                </tbody>
	            </table>
	            
	            <h3>💰 Thanh toán</h3>
	            <p><b>Tổng tiền:</b> <span style="color:#F42525; font-size:18px;">%s</span></p>
	            <p><b>Phương thức:</b> %s</p>
	            
	            <h3>📦 Thông tin nhận hàng</h3>
	            <p><b>Người nhận:</b> %s</p>
	            <p><b>SĐT:</b> %s</p>
	            <p><b>Địa chỉ:</b> %s</p>
	            
	            <br>
	            <p>Nếu bạn có bất kỳ câu hỏi nào, hãy liên hệ với chúng tôi.</p>
	            
	            <p style="margin-top:20px;">Trân trọng,<br><b>PiShop Team</b></p>
	        </div>
	        """,
	            order.getId(),
	            order.getCreateAt(), 
	            itemsHtml,
	            formatPrice(order.getTotalAmount()),
	            order.getPaymentMethod(),
	            order.getToName(),
	            order.getToPhone(),
	            order.getToAddress()
	    );
	    String subject = String.format("Xác nhận đơn hàng từ PiShop");
	    
	    try {
	    	 emailService.sendHtmlMail(
	 	            account.getEmail(),
	 	            subject,
	 	            message
	 	    );
		} catch (Exception e) {
			throw new RuntimeException("Send mail failed", e);
		}
	   
	}



	private String formatPrice(BigDecimal price) {
	    return NumberFormat
	            .getInstance(new Locale("vi", "VN"))
	            .format(price);
	}

	// 1. Lấy tất cả đơn hàng theo accountId (sort by date desc)
    public List<Order> getOrdersByAccountId(Long accountId) {
        return orderRepository.findByAccountIdOrderByCreateAtDesc(accountId);
    }

    // 2. Lấy đơn hàng theo orderStatus
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByOrderStatusOrderByCreateAtDesc(status);
    }

    // 3. Lấy tất cả đơn hàng chưa thanh toán của accountId (BANK, UNPAID)
    public List<Order> getUnpaidBankOrdersByAccountId(Long accountId) {
        return orderRepository.findByAccountIdAndPaymentMethodAndPayStatusOrderByCreateAtDesc(
            accountId, "BANK", PayStatus.UNPAID);
    }

    // 4. Yêu cầu hủy đơn hàng
    public boolean requestCancelOrder(Long orderId, Long accountId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!order.getAccount().getId().equals(accountId)) {
            throw new RuntimeException("Bạn không có quyền hủy đơn hàng này");
        }
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Đơn hàng đã bị hủy trước đó");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return true;
    }
}
