package com.backend.pishop.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

import com.backend.pishop.enums.AccountRank;
import com.backend.pishop.enums.AccountRole;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    
    @Column(name = "first_name")
	String firstName;
    
    @Column(name = "last_name")
	String lastName;
    
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @Column(name = "is_active")
    private boolean isActive;

    private String image;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private AccountRole role;
  
    @Column(name = "point")
    private Integer point;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "last_activity")
    private LocalDateTime lastActivity;

    // 1 Account có nhiều Address
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    // 1 Account có 1 Cart
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    // 1 Account có 1 User
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountVoucher> accountVoucher = new ArrayList<>();
    
    
    public AccountRank getRank() {
        if (point == null || point == 0) return AccountRank.NEW;
        if (point < 200) return AccountRank.BRONZE;
        if (point < 500) return AccountRank.SILVER;
        if (point < 700) return AccountRank.GOLD;
        if (point < 1000) return AccountRank.DIAMOND;
        return AccountRank.VIP;
    }

    
}

