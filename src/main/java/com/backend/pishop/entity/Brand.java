package com.backend.pishop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    private String image;

    private String website;

    // 1 Brand có nhiều Product
    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Product> products;
}