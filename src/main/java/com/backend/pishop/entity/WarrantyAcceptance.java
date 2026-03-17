package com.backend.pishop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.backend.pishop.enums.DataStatus;
import com.backend.pishop.enums.DeviceAccountStatus;

@Entity
@Table(name = "warranty_acceptances")
@Getter
@Setter
public class WarrantyAcceptance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ngày nhận bảo hành
    @Column(name = "start_date")
    private LocalDateTime startDate;

    // thông tin khách hàng
    @Column(name = "customer_name")
    private String customerName;

    private String phone;

    private String email;

    // serial / imei
    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "product_name")
    private String productName;

    // tình trạng máy
    @Column(name = "product_status")
    private String productStatus;

    // lỗi khách báo
    @Column(columnDefinition = "TEXT")
    private String problem;

    // phụ kiện đi kèm
    private String accessories;

    // trạng thái tài khoản (icloud, google, logout...)
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private DeviceAccountStatus accountStatus;

    // password thiết bị nếu có
    private String password;

    // trạng thái dữ liệu
    @Enumerated(EnumType.STRING)
    @Column(name = "data_status")
    private DataStatus dataStatus;

    // ước tính thời gian sửa
    private String estimate;

    // liên kết product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serial_number", referencedColumnName = "serial_number")
    private SerialNumber serialNumberRef;
}