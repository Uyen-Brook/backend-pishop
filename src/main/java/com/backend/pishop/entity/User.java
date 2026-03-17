package com.backend.pishop.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	Integer id;
	
	@Column(name = "full_name")
	String fullName;

	@Column(name = "date_of_birth")
	LocalDate dateOfBirth;

	@Column(name = "phone_number")
	String phoneNumber;

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
	Account account;
}
