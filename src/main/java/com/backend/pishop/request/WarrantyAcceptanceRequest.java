package com.backend.pishop.request;

import com.backend.pishop.enums.DataStatus;
import com.backend.pishop.enums.DeviceAccountStatus;

import lombok.Data;

@Data
public class WarrantyAcceptanceRequest {

    // tìm máy bằng serial
    private String serialNumber;

    // customer info
    private String customerName;

    private String phone;

    private String email;

    // tình trạng máy
    private String productStatus;

    // lỗi khách báo
    private String problem;

    // phụ kiện
    private String accessories;

    // trạng thái tài khoản
    private DeviceAccountStatus accountStatus;

    // password máy
    private String password;

    // trạng thái dữ liệu
    private DataStatus dataStatus;

    // thời gian sửa dự kiến
    private String estimate;
}