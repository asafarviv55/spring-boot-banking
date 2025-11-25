package com.alfa.bank.controllers;

import com.alfa.bank.models.Account;
import com.alfa.bank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestParam Long userId,
            @RequestParam Account.AccountType type,
            @RequestParam(required = false) String currency) {
        return ResponseEntity.ok(accountService.createAccount(userId, type, currency));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(accountService.deposit(id, amount));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Long id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(accountService.withdraw(id, amount));
    }

    @PostMapping("/{id}/freeze")
    public ResponseEntity<Account> freeze(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.freezeAccount(id));
    }

    @PostMapping("/{id}/unfreeze")
    public ResponseEntity<Account> unfreeze(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.unfreezeAccount(id));
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<Account> close(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.closeAccount(id));
    }

    @PutMapping("/{id}/overdraft-limit")
    public ResponseEntity<Account> setOverdraftLimit(@PathVariable Long id, @RequestParam BigDecimal limit) {
        return ResponseEntity.ok(accountService.setOverdraftLimit(id, limit));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> getUserAccounts(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getUserAccounts(userId));
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<Account>> getActiveAccounts(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getActiveUserAccounts(userId));
    }

    @GetMapping("/user/{userId}/total-balance")
    public ResponseEntity<Map<String, BigDecimal>> getTotalBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(Map.of("totalBalance", accountService.getTotalBalance(userId)));
    }

    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<Account> getByNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountByNumber(accountNumber));
    }
}
