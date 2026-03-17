package com.backend.pishop.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(name = "specific_address")
    private String specificAddress;

    // Address -> Account (1-1)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    // Address -> Province (Many Address thuộc 1 Province)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_code", referencedColumnName = "code")
    private Province province;

    // Address -> Ward (Many Address thuộc 1 Ward)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_code", referencedColumnName = "code")
    private Ward ward;
}