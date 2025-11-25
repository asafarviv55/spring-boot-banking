package com.alfa.bank.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(precision = 19, scale = 4)
    private BigDecimal balance;

    @Column(precision = 19, scale = 4)
    private BigDecimal availableBalance;

    private String currency;
    private BigDecimal interestRate;
    private BigDecimal overdraftLimit;
    private Timestamp openedAt;
    private Timestamp closedAt;
    private Timestamp lastTransactionAt;

    public enum AccountType {
        CHECKING, SAVINGS, MONEY_MARKET, CERTIFICATE_DEPOSIT, BUSINESS
    }

    public enum AccountStatus {
        ACTIVE, DORMANT, FROZEN, CLOSED
    }

    @PrePersist
    protected void onCreate() {
        openedAt = new Timestamp(System.currentTimeMillis());
        if (status == null) status = AccountStatus.ACTIVE;
        if (balance == null) balance = BigDecimal.ZERO;
        if (availableBalance == null) availableBalance = BigDecimal.ZERO;
        if (currency == null) currency = "USD";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public AccountType getType() { return type; }
    public void setType(AccountType type) { this.type = type; }
    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public BigDecimal getAvailableBalance() { return availableBalance; }
    public void setAvailableBalance(BigDecimal availableBalance) { this.availableBalance = availableBalance; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public BigDecimal getInterestRate() { return interestRate; }
    public void setInterestRate(BigDecimal interestRate) { this.interestRate = interestRate; }
    public BigDecimal getOverdraftLimit() { return overdraftLimit; }
    public void setOverdraftLimit(BigDecimal overdraftLimit) { this.overdraftLimit = overdraftLimit; }
    public Timestamp getOpenedAt() { return openedAt; }
    public void setOpenedAt(Timestamp openedAt) { this.openedAt = openedAt; }
    public Timestamp getClosedAt() { return closedAt; }
    public void setClosedAt(Timestamp closedAt) { this.closedAt = closedAt; }
    public Timestamp getLastTransactionAt() { return lastTransactionAt; }
    public void setLastTransactionAt(Timestamp lastTransactionAt) { this.lastTransactionAt = lastTransactionAt; }
}
