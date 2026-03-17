package com.backend.pishop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String taxcode;

    private String email;

    private String phone;

    private String logo;

    private String address;

    private String note;

    // 1 Supplier có nhiều Product
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<Product> products;
}