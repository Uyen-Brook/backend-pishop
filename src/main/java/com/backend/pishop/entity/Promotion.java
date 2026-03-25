package com.backend.pishop.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "promotions")
public class Promotion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
    String name;
    String discountType;   // percent, fixed_amount
    String discountValue;
    BigDecimal minOrderValue;
    boolean isActive;
    LocalDate start_date;
    LocalDate endDate;
    
}
