package com.backend.pishop.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import com.backend.pishop.config.VNPayConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService {

    public String createOrder(int total, String orderInfor, String urlReturn){
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String orderType = "order-type";
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(total*100));
        vnp_Params.put("vnp_CurrCode", "VND");
        
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfor);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);

        urlReturn += VNPayConfig.vnp_Returnurl;
        vnp_Params.put("vnp_ReturnUrl", urlReturn);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }

    public int orderReturn(HttpServletRequest request){
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = null;
            String fieldValue = null;
            try {
        
                fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = VNPayConfig.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }
    
//    public int orderReturn(HttpServletRequest request) {
//
//        // 1. Lấy toàn bộ params (KHÔNG encode sai như trước)
//        Map<String, String> fields = new HashMap<>();
//        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
//            String fieldName = params.nextElement();
//            String fieldValue = request.getParameter(fieldName);
//            if (fieldValue != null && fieldValue.length() > 0) {
//                fields.put(fieldName, fieldValue);
//            }
//        }
//
//        // 2. Lấy secure hash từ VNPay
//        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
//
//        // 3. Remove hash fields trước khi verify
//        fields.remove("vnp_SecureHash");
//        fields.remove("vnp_SecureHashType");
//
//        // 4. Tạo lại chữ ký
//        String signValue = VNPayConfig.hashAllFields(fields);
//
//        // ❌ Nếu sai chữ ký → reject ngay
//        if (!signValue.equals(vnp_SecureHash)) {
//            return -1; // dữ liệu bị giả mạo
//        }
//
//        // 5. Extract dữ liệu quan trọng
//        String txnRef = request.getParameter("vnp_TxnRef");
//        String amount = request.getParameter("vnp_Amount");
//        String orderInfo = request.getParameter("vnp_OrderInfo");
//
//        String responseCode = request.getParameter("vnp_ResponseCode");
//        String transactionStatus = request.getParameter("vnp_TransactionStatus");
//
//        String transactionNo = request.getParameter("vnp_TransactionNo");
//        String bankCode = request.getParameter("vnp_BankCode");
//        String payDate = request.getParameter("vnp_PayDate");
//
//        String rawData = request.getQueryString(); // 🔥 debug cực quan trọng
//
//        // 6. TODO: Tìm giao dịch trong DB
//        // Payment payment = paymentRepository.findByTxnRef(txnRef);
//
//        // ❗ chống double payment (rất quan trọng)
//        // if (payment.getStatus().equals("SUCCESS")) return 1;
//
//        // 7. Xử lý kết quả
//        if ("00".equals(responseCode) && "00".equals(transactionStatus)) {
//
//            // ✅ SUCCESS
//            // payment.setStatus("SUCCESS");
//
//            // TODO: update DB
//            // payment.setTransactionNo(transactionNo);
//            // payment.setBankCode(bankCode);
//            // payment.setPayDate(payDate);
//            // payment.setResponseCode(responseCode);
//            // payment.setRawData(rawData);
//
//            return 1;
//
//        } else {
//
//            // ❌ FAIL
//            // payment.setStatus("FAIL");
//            // payment.setResponseCode(responseCode);
//            // payment.setRawData(rawData);
//
//            return 0;
//        }
//    }

}
