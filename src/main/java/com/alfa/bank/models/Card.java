package com.alfa.bank.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String cardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private CardType type;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    private String nameOnCard;
    private LocalDate expiryDate;
    private String cvvHash;

    @Column(precision = 19, scale = 4)
    private BigDecimal dailyLimit;

    @Column(precision = 19, scale = 4)
    private BigDecimal monthlyLimit;

    @Column(precision = 19, scale = 4)
    private BigDecimal creditLimit;

    private boolean isContactless;
    private boolean isInternational;
    private boolean isOnlinePurchase;
    private String pin;
    private Timestamp issuedAt;
    private Timestamp blockedAt;

    public enum CardType {
        DEBIT, CREDIT, PREPAID, VIRTUAL
    }

    public enum CardStatus {
        ACTIVE, INACTIVE, BLOCKED, EXPIRED, CANCELLED
    }

    @PrePersist
    protected void onCreate() {
        issuedAt = new Timestamp(System.currentTimeMillis());
        if (status == null) status = CardStatus.INACTIVE;
        isContactless = true;
        isOnlinePurchase = true;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public CardType getType() { return type; }
    public void setType(CardType type) { this.type = type; }
    public CardStatus getStatus() { return status; }
    public void setStatus(CardStatus status) { this.status = status; }
    public String getNameOnCard() { return nameOnCard; }
    public void setNameOnCard(String nameOnCard) { this.nameOnCard = nameOnCard; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public String getCvvHash() { return cvvHash; }
    public void setCvvHash(String cvvHash) { this.cvvHash = cvvHash; }
    public BigDecimal getDailyLimit() { return dailyLimit; }
    public void setDailyLimit(BigDecimal dailyLimit) { this.dailyLimit = dailyLimit; }
    public BigDecimal getMonthlyLimit() { return monthlyLimit; }
    public void setMonthlyLimit(BigDecimal monthlyLimit) { this.monthlyLimit = monthlyLimit; }
    public BigDecimal getCreditLimit() { return creditLimit; }
    public void setCreditLimit(BigDecimal creditLimit) { this.creditLimit = creditLimit; }
    public boolean isContactless() { return isContactless; }
    public void setContactless(boolean contactless) { isContactless = contactless; }
    public boolean isInternational() { return isInternational; }
    public void setInternational(boolean international) { isInternational = international; }
    public boolean isOnlinePurchase() { return isOnlinePurchase; }
    public void setOnlinePurchase(boolean onlinePurchase) { isOnlinePurchase = onlinePurchase; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    public Timestamp getIssuedAt() { return issuedAt; }
    public void setIssuedAt(Timestamp issuedAt) { this.issuedAt = issuedAt; }
    public Timestamp getBlockedAt() { return blockedAt; }
    public void setBlockedAt(Timestamp blockedAt) { this.blockedAt = blockedAt; }
}
