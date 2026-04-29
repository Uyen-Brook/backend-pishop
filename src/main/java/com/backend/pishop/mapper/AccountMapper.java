package com.backend.pishop.mapper;

import com.backend.pishop.entity.Account;
import com.backend.pishop.response.AccountResponse;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public static AccountResponse toResponse(Account acc) {
        if (acc == null) return null;
        AccountResponse res = new AccountResponse();

        // ===== BASIC =====
        res.setId(acc.getId());
        res.setFirstName(acc.getFirstName());
        res.setLastName(acc.getLastName());
        res.setEmail(acc.getEmail());

        // ===== STATUS =====
        res.setActive(acc.isActive());

        // ===== PROFILE =====
        res.setImage(acc.getImage());

        // ===== ROLE =====
        res.setRole(acc.getRole());

        // ===== BUSINESS =====
        res.setPoint(acc.getPoint());
        res.setRank(acc.getRank()); // lấy từ method trong entity

        // ===== AUDIT =====
        res.setCreateAt(acc.getCreateAt());
        res.setLastActivity(acc.getLastActivity());

        return res;
    }
    public static List<AccountResponse> toResponseList(List<Account> accounts) {
        if (accounts == null) return List.of();

        return accounts.stream()
                .map(AccountMapper::toResponse)
                .toList();
    }
}