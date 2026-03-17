package com.backend.pishop.entity;


import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;

public class Account {
	@Column(name = "account_id")
	Integer id;
	
	@Column(name = "email")
	String email;
	
	@Column(name = "password")
	String passWord;
	
	@Column(name = "is_active")
	boolean isActive;
	
	@Column(name = "image")
	String image;
	
	
	@Column(name = "delete_at")
	LocalDateTime deleteAt;
	
	@Column(name = "create_at")
	LocalDateTime creatAt;
	
	@UpdateTimestamp
	@Column(name = "last_activity")
	LocalDateTime lastActivity;
}
