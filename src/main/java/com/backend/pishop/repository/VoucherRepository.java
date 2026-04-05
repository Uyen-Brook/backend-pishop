package com.backend.pishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.pishop.entity.Voucher;

public interface VoucherRepository  extends JpaRepository<Voucher, Long>{
	
}
