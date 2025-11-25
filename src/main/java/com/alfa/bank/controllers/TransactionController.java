package com.alfa.bank.controllers;

import com.alfa.bank.models.Transaction;
import com.alfa.bank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String description) {
        return ResponseEntity.ok(transactionService.transfer(fromAccountId, toAccountId, amount, description));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(
            @RequestParam Long accountId,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String description) {
        return ResponseEntity.ok(transactionService.deposit(accountId, amount, description));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(
            @RequestParam Long accountId,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String description) {
        return ResponseEntity.ok(transactionService.withdraw(accountId, amount, description));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getAccountTransactions(accountId));
    }

    @GetMapping("/account/{accountId}/range")
    public ResponseEntity<List<Transaction>> getByDateRange(
            @PathVariable Long accountId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        Timestamp start = Timestamp.valueOf(startDate + " 00:00:00");
        Timestamp end = Timestamp.valueOf(endDate + " 23:59:59");
        return ResponseEntity.ok(transactionService.getTransactionsByDateRange(accountId, start, end));
    }

    @GetMapping("/ref/{transactionRef}")
    public ResponseEntity<Transaction> getByRef(@PathVariable String transactionRef) {
        return ResponseEntity.ok(transactionService.getByRef(transactionRef));
    }
}
