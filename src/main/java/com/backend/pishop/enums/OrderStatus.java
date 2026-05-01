package com.backend.pishop.enums;

public enum OrderStatus {
	PENDING("Chờ thanh toán"),
	CONFIRMATION("Chờ xác nhận"),
	PAID("Đã thanh toán"),
	CONFIRMED("Đã xác nhận"),
	SHIPPING("Đang giao hàng"),
	DELIVERED("Đã giao hàng"),
	CANCELLED("Đã hủy");
	private final String description;
	OrderStatus(String description) {
		this.description = description;
	}
}
