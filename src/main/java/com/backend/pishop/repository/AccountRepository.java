package com.backend.pishop.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.pishop.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByEmail(String email);
	Optional<Account> findById(Long Id);
	boolean existsByEmail(String email);
}
