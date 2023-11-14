package com.pichincha.pichinchaapi.controller;

import com.pichincha.pichinchaapi.dto.AccountDTO;
import com.pichincha.pichinchaapi.entity.Account;
import com.pichincha.pichinchaapi.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        return accountService.findById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountDTO accountDTO) {
        Account account = convertToEntity(accountDTO);
        Account createdAccount = accountService.save(account);
        return ResponseEntity.ok(convertToDTO(createdAccount));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody @Valid AccountDTO accountDTO) {
        return accountService.findById(id)
                .map(account -> {
                    account.setAccountNumber(accountDTO.getAccountNumber());
                    account.setAccountType(accountDTO.getAccountType());
                    account.setInitialBalance(accountDTO.getInitialBalance());
                    return convertToDTO(accountService.save(account));
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable Long id) {
        return accountService.findById(id)
                .map(account -> {
                    accountService.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private AccountDTO convertToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setInitialBalance(account.getInitialBalance());
        return accountDTO;
    }

    private Account convertToEntity(AccountDTO accountDTO) {
        return Account.builder()
                .id(accountDTO.getId())
                .accountNumber(accountDTO.getAccountNumber())
                .accountType(accountDTO.getAccountType())
                .initialBalance(accountDTO.getInitialBalance())
                .build();
    }
}

