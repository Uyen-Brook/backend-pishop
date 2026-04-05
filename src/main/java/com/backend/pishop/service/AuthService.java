package com.backend.pishop.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.pishop.entity.Account;
import com.backend.pishop.enums.AccountRole;
import com.backend.pishop.repository.AccountRepository;
import com.backend.pishop.request.LoginRequest;
import com.backend.pishop.request.RegisterRequest;
import com.backend.pishop.response.AuthResponse;
import com.backend.pishop.secuirity.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    
    
    public ResponseEntity<?> register(RegisterRequest request) {

        // Kiểm tra email tồn tại
        if (accountRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        // Tạo account mới
        Account account = new Account();
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setActive(true);
        account.setRole(AccountRole.USER);
        account.setPoint(0);
        account.setCreateAt(LocalDateTime.now());

        accountRepository.save(account);

        return ResponseEntity.ok("Register successful. Please login.");
    }


    public ResponseEntity<?> login(LoginRequest request) {

        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Sai email hoặc mật khẩu"));

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new UsernameNotFoundException("Sai email hoặc mật khẩu");
        }

        String token = jwtUtil.generateToken(account);

        // build response
        AuthResponse response = new AuthResponse();

        AuthResponse.AccountInfo info = new AuthResponse.AccountInfo();
        info.setId(account.getId());
        info.setEmail(account.getEmail());
        info.setRole(account.getRole().name());

        response.setAccount(info);
        response.setToken(token); 

        return ResponseEntity.ok(response);
    }
}