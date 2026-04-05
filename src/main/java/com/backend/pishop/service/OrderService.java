package com.backend.pishop.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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

    @Transactional
    public Order createOrder(OrderRequest request) {

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
            if(product.getQuanlity()< itemReq.getQuantity()) {
            	throw new RuntimeException("Sản phẩm không đủ hàng");
            };
            // xử lý khuyến mãi
            BigDecimal price = product.getPrice();
            	
           
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(price);
            item.setOrder(order);
//            item.setPromotionId();

            orderItems.add(item);

            totalAmount = totalAmount.add(
                    price.multiply(BigDecimal.valueOf(itemReq.getQuantity()))
            );
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        
        
        // 5. Xử lý voucher
//        BigDecimal discountAmount = BigDecimal.ZERO;
//
//        if (request.getVoucherCode() != null) {
//
//            Voucher voucher = voucherRepository
//                    .findByVoucherCode(request.getVoucherCode())
//                    .orElseThrow(() -> new RuntimeException("Voucher not found"));
//
//            validateVoucher(voucher, account, totalAmount);
//
//            discountAmount = calculateDiscount(voucher, totalAmount);
//
//            order.setVoucher(voucher);
//            order.setVoucherCode(voucher.getVoucherCode());
//
//            // 🔥 tăng usage
//            voucher.setCurrentUsage(voucher.getCurrentUsage() + 1);
//
//            // 🔥 mark AccountVoucher = USED
//            AccountVoucher av = accountVoucherRepository
//                    .findByAccountAndVoucher(account, voucher)
//                    .orElseThrow(() -> new RuntimeException("Voucher not owned"));
//
//            av.setVoucherStatus(VoucherStatus.USED);
//            av.setUsedAt(LocalDateTime.now());
//        }
//
//        order.setDiscountAmount(discountAmount);
//
//        // 6. Ship fee (hardcode demo)
//        BigDecimal shipFee = BigDecimal.valueOf(30000);
//        order.setShipFee(shipFee);
//
//        // 7. Final price (nếu bạn muốn lưu)
//        BigDecimal finalAmount = totalAmount
//                .subtract(discountAmount)
//                .add(shipFee);

        // 👉 bạn có thể thêm field finalAmount vào Order nếu cần

        // 8. Save
        return orderRepository.save(order);
    }
    
    private BigDecimal calculateDiscount(Voucher voucher, BigDecimal totalAmount) {

        BigDecimal discount;

        if (voucher.getDiscountType() == DiscountType.PERCENT) {
            discount = totalAmount.multiply(voucher.getDiscountValue())
                    .divide(BigDecimal.valueOf(100));
        } else {
            discount = voucher.getDiscountValue();
        }

        // cap max discount
        if (voucher.getMaxDiscountAmount() != null &&
            discount.compareTo(voucher.getMaxDiscountAmount()) > 0) {
            discount = voucher.getMaxDiscountAmount();
        }

        return discount;
    }
    private BigDecimal bestProductPriceWithPromotion(Long id, BigDecimal price) {
    	List<Promotion> promotions = promotionRepository
    	        .findActivePromotionsByProductId(id);

    	BigDecimal finalPrice = price;

    	if (!promotions.isEmpty()) {

    	    BigDecimal bestPrice = finalPrice;

    	    for (Promotion promo : promotions) {

    	        BigDecimal discountedPrice = finalPrice;

    	        if (promo.getDiscountType() == DiscountType.PERCENT) {
    	            discountedPrice = finalPrice.subtract(
    	                    finalPrice.multiply(promo.getDiscountValue())
    	                            .divide(BigDecimal.valueOf(100))
    	            );
    	        } else if (promo.getDiscountType() == DiscountType.FIXED_AMOUNT) {
    	            discountedPrice = finalPrice.subtract(promo.getDiscountValue());
    	        }

    	        // tránh âm tiền
    	        if (discountedPrice.compareTo(BigDecimal.ZERO) < 0) {
    	            discountedPrice = BigDecimal.ZERO;
    	        }

    	        // lấy giá tốt nhất
    	        if (discountedPrice.compareTo(bestPrice) < 0) {
    	            bestPrice = discountedPrice;
    	        }
    	    }

    	    finalPrice = bestPrice;
    	   
    	}
    	
    	return finalPrice;
    }
    
    
    
    //    private void SendtoMail() {
//    	 //gửi email xác nhận
//        String message = """
//        <h1>Xác nhận đặt hàng thành công!</h1>
//        <div style="font-family: Arial, sans-serif; line-height: 1.6;">
//          <h4>Thông tin đặt hàng tại PiShop</h4>
//          <h3><b>Đơn hàng: %s</b></h3>
//          <p>Số vé: %d</p>
//          <p>Ngày khởi hành: %s</p>
//          <p><strong>Thành tiền: %sđ</strong></p>
//          <br>
//          <h4>Thông tin khách hàng</h4>
//          <p>Họ tên: %s</p>
//          <p>Số điện thoại: %s</p>
//          <p>Địa chỉ: %s</p>
//          <br>
//          <i>Cảm ơn quý khách đã tin tưởng TravelGo!</i>
//        </div>
//        """.formatted(
//                item.getTitleTour(),
//                amountTicket,
//                item.getDateTour(),
//                NumberFormat.getInstance(new Locale("vi", "VN"))
//                        .format(BigDecimal.valueOf(amountTicket).multiply(item.getPrice())),
//                user.getFullname(),
//                user.getPhoneNumber(),
//                address
//        );
//        emailService.sendHtmlMail(account.getEmail(), "Xác nhận đặt tour du lịch", message);
//    }
}
