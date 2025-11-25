package com.alfa.bank.services;

import com.alfa.bank.models.Account;
import com.alfa.bank.models.Transaction;
import com.alfa.bank.repositories.AccountRepository;
import com.alfa.bank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transaction transfer(Long fromAccountId, Long toAccountId, BigDecimal amount, String description) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (fromAccount.getAvailableBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // Debit from source
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        fromAccount.setAvailableBalance(fromAccount.getAvailableBalance().subtract(amount));
        fromAccount.setLastTransactionAt(new Timestamp(System.currentTimeMillis()));

        // Credit to destination
        toAccount.setBalance(toAccount.getBalance().add(amount));
        toAccount.setAvailableBalance(toAccount.getAvailableBalance().add(amount));
        toAccount.setLastTransactionAt(new Timestamp(System.currentTimeMillis()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setTransactionRef(generateTransactionRef());
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setType(Transaction.TransactionType.TRANSFER);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setCurrency(fromAccount.getCurrency());
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        transaction.setCompletedAt(new Timestamp(System.currentTimeMillis()));
        transaction.setBalanceAfter(fromAccount.getBalance());

        return transactionRepository.save(transaction);
    }

    public Transaction deposit(Long accountId, BigDecimal amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        account.setAvailableBalance(account.getAvailableBalance().add(amount));
        account.setLastTransactionAt(new Timestamp(System.currentTimeMillis()));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionRef(generateTransactionRef());
        transaction.setToAccount(account);
        transaction.setType(Transaction.TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setCurrency(account.getCurrency());
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        transaction.setCompletedAt(new Timestamp(System.currentTimeMillis()));
        transaction.setBalanceAfter(account.getBalance());

        return transactionRepository.save(transaction);
    }

    public Transaction withdraw(Long accountId, BigDecimal amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getAvailableBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));
        account.setAvailableBalance(account.getAvailableBalance().subtract(amount));
        account.setLastTransactionAt(new Timestamp(System.currentTimeMillis()));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionRef(generateTransactionRef());
        transaction.setFromAccount(account);
        transaction.setType(Transaction.TransactionType.WITHDRAWAL);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setCurrency(account.getCurrency());
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        transaction.setCompletedAt(new Timestamp(System.currentTimeMillis()));
        transaction.setBalanceAfter(account.getBalance());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAccountTransactions(Long accountId) {
        return transactionRepository.findByFromAccountIdOrderByCreatedAtDesc(accountId);
    }

    public List<Transaction> getTransactionsByDateRange(Long accountId, Timestamp startDate, Timestamp endDate) {
        return transactionRepository.findByAccountAndDateRange(accountId, startDate, endDate);
    }

    public Transaction getByRef(String transactionRef) {
        return transactionRepository.findByTransactionRef(transactionRef)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    private String generateTransactionRef() {
        return "TXN" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
