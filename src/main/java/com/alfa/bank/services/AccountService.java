package com.alfa.bank.services;

import com.alfa.bank.models.Account;
import com.alfa.bank.models.User;
import com.alfa.bank.repositories.AccountRepository;
import com.alfa.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public Account createAccount(Long userId, Account.AccountType type, String currency) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setUser(user);
        account.setAccountNumber(generateAccountNumber());
        account.setType(type);
        account.setCurrency(currency != null ? currency : "USD");
        account.setBalance(BigDecimal.ZERO);
        account.setAvailableBalance(BigDecimal.ZERO);

        return accountRepository.save(account);
    }

    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        account.setAvailableBalance(account.getAvailableBalance().add(amount));
        account.setLastTransactionAt(new Timestamp(System.currentTimeMillis()));

        return accountRepository.save(account);
    }

    public Account withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getAvailableBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));
        account.setAvailableBalance(account.getAvailableBalance().subtract(amount));
        account.setLastTransactionAt(new Timestamp(System.currentTimeMillis()));

        return accountRepository.save(account);
    }

    public Account freezeAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setStatus(Account.AccountStatus.FROZEN);
        return accountRepository.save(account);
    }

    public Account unfreezeAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setStatus(Account.AccountStatus.ACTIVE);
        return accountRepository.save(account);
    }

    public Account closeAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new RuntimeException("Account must have zero balance to close");
        }

        account.setStatus(Account.AccountStatus.CLOSED);
        account.setClosedAt(new Timestamp(System.currentTimeMillis()));
        return accountRepository.save(account);
    }

    public Account setOverdraftLimit(Long accountId, BigDecimal limit) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setOverdraftLimit(limit);
        return accountRepository.save(account);
    }

    public List<Account> getUserAccounts(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    public List<Account> getActiveUserAccounts(Long userId) {
        return accountRepository.findByUserIdAndStatus(userId, Account.AccountStatus.ACTIVE);
    }

    public BigDecimal getTotalBalance(Long userId) {
        BigDecimal total = accountRepository.getTotalBalanceByUser(userId);
        return total != null ? total : BigDecimal.ZERO;
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }
}
