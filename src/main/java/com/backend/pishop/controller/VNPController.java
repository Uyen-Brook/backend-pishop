package com.backend.pishop.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.backend.pishop.service.VNPayService;

@org.springframework.stereotype.Controller
@RequestMapping("/api")
public class VNPController {
    @Autowired
    private VNPayService vnPayService;
    
    @PostMapping("/submitOrder")
    public ResponseEntity<?> submidOrder(@RequestParam("amount") int orderTotal,
                            @RequestParam("orderInfo") String orderInfo,
                            HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
        
        Map<String, String> res = new HashMap<>();
        res.put("paymentUrl", vnpayUrl);

        return ResponseEntity.ok(res);
      
    }

    @GetMapping("/vnpay-payment")
    public String vnpayReturn(HttpServletRequest request, Model model) {

        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        
        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice + " VNĐ");
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

//        Map<String, Object> res = new HashMap<>();
//        res.put("orderId", orderInfo);
//        res.put("totalPrice", totalPrice);
//        res.put("paymentTime", paymentTime);
//        res.put("transactionId", transactionId);
//        res.put("status", paymentStatus == 1 ? "SUCCESS" : "FAILED");

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }
}
