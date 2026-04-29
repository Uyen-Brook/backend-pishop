package com.backend.pishop.service.admin;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.backend.pishop.entity.Account;
import com.backend.pishop.mapper.AccountMapper;
import com.backend.pishop.repository.AccountRepository;
import com.backend.pishop.request.AccountRequest;
import com.backend.pishop.response.AccountResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminAccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;
    // CREATE
    public AccountResponse createAccount(AccountRequest req) {
    	Account account = new Account();

        account.setFirstName(req.getFirstName());
        account.setLastName(req.getLastName());
        account.setEmail(req.getEmail());
        account.setImage(req.getImage());

        account.setPassword(passwordEncoder.encode(req.getPassword()));
        account.setActive(true);
        account.setCreateAt(LocalDateTime.now());

        Account saved = accountRepository.save(account);

        return AccountMapper.toResponse(saved);
    }

    // READ
    public AccountResponse getAccountById(Long id) {
    	   Account account = accountRepository.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));

           return accountMapper.toResponse(account);
    }

    // UPDATE
    public AccountResponse updateAccount(Long id, AccountRequest updated) {
        Account account =  accountRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Không tìm thấy tài khoản"));
        account.setFirstName(updated.getFirstName());
        account.setLastName(updated.getLastName());
        account.setEmail(updated.getEmail());
        if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
            account.setPassword(passwordEncoder.encode(updated.getPassword()));
        }
        account.setRole(updated.getRole());
        account.setImage(updated.getImage());
        accountRepository.save(account);
        return  accountMapper.toResponse(account);
    }

    // DELETE
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Không tìm thấy tài khoản"));
        accountRepository.delete(account);
        return;
    }

    // GET ALL (không phân trang)
    public List<AccountResponse> getAllAccounts() {
        return accountMapper.toResponseList( accountRepository.findAll());
        		
    }
    
    // sửa mật khẩu
    public AccountResponse resetPassword(Long accountId, String rawPassword) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setPassword(passwordEncoder.encode(rawPassword));
        accountRepository.save(account);
        return  accountMapper.toResponse(account);
    }
    // khóa tài khoản
    public AccountResponse lockAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setActive(false);
        accountRepository.save(account);
        return  accountMapper.toResponse(account);
    }
    // mở khóa
    public AccountResponse unlockAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setActive(true);
        accountRepository.save(account);
        return accountMapper.toResponse(account);
    }

    public List<AccountResponse> searchAccounts(String q) {
        List<Account> accounts =accountRepository.findByEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(q, q, q);
        return accountMapper.toResponseList(accounts);
    }


    public java.util.List<AccountResponse> listByRole(com.backend.pishop.enums.AccountRole role) {
         List<Account> accounts = accountRepository.findByRole(role);
         return accountMapper.toResponseList(accounts);
    }
}
