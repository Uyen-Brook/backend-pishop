package com.backend.pishop.request;

import java.util.List;

import com.backend.pishop.enums.PaymentMethod;

import lombok.Data;
@Data
public class OrderRequest {
	private Long accountId;
    private String toName;
    private String toPhone;
    private String toProvinceCode;
    private String toWardCode;
    private String toAddress;
    private String voucherCode;
    // paystatus, ordersatatus sẽ được set mặc định trong service
    private PaymentMethod paymentMethod;
    private List<OrderItemRequest> items;
}
