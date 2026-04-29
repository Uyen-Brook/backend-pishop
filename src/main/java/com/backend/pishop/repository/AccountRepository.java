package com.backend.pishop.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.pishop.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByEmail(String email);
	Optional<Account> findById(Long Id);
	boolean existsByEmail(String email);

	// additional helper queries for admin
	List<Account> findByRole(com.backend.pishop.enums.AccountRole role);
	List<Account> findByEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
	        String email, String firstName, String lastName);

}