package com.backend.pishop.controller.admin;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.backend.pishop.entity.Account;
import com.backend.pishop.service.admin.AdminAccountService;
import com.backend.pishop.config.APIResponse;

import com.backend.pishop.enums.AccountRole;
import com.backend.pishop.request.AccountRequest;
import com.backend.pishop.response.AccountResponse;

@RestController
@RequestMapping("api/admin/accounts")
@RequiredArgsConstructor
public class AdminAccountController {

    private final AdminAccountService accountService;
    
 // CREATE
    @PostMapping("/create")
    public AccountResponse create(@RequestBody AccountRequest account) {
        return  accountService.createAccount(account);
    }

    // READ (get by id)
    @GetMapping("/{id}")
    public AccountResponse getById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public AccountResponse update(@PathVariable Long id, @RequestBody AccountRequest account) {
        return accountService.updateAccount(id, account);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public APIResponse<String> delete(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return APIResponse.success("Account deleted");
    }

    // GET ALL (không phân trang)
    // ✅ GET ALL ACCOUNTS (thiếu cái này)
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(
            accountService.getAllAccounts()
        );
    }

    @PostMapping("/{id}/reset-password")
    public APIResponse<String> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        accountService.resetPassword(id, newPassword);
        return APIResponse.success("Password reset");
    }

    @PostMapping("/{id}/lock")
    public APIResponse<String> lock(@PathVariable Long id) {
        accountService.lockAccount(id);
        return APIResponse.success("Account locked");
    }

    @PostMapping("/{id}/unlock")
    public APIResponse<String> unlock(@PathVariable Long id) {
        accountService.unlockAccount(id);
        return APIResponse.success("Account unlocked");
    }

    @GetMapping("/search")
    public List<AccountResponse> search(@RequestParam(defaultValue = "") String q) {
        return accountService.searchAccounts(q);
    }

    @GetMapping("/by-role")
    public List<AccountResponse> byRole(@RequestParam AccountRole role) {
        return accountService.listByRole(role);
    }
}
