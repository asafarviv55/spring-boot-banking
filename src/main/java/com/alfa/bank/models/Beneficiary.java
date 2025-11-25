package com.alfa.bank.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "beneficiaries")
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String nickname;
    private String accountNumber;
    private String bankCode;
    private String bankName;
    private String beneficiaryName;

    @Enumerated(EnumType.STRING)
    private BeneficiaryType type;

    private boolean isVerified;
    private boolean isActive;
    private Timestamp createdAt;
    private Timestamp lastUsedAt;

    public enum BeneficiaryType {
        INTERNAL, DOMESTIC, INTERNATIONAL
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        isActive = true;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getBankCode() { return bankCode; }
    public void setBankCode(String bankCode) { this.bankCode = bankCode; }
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public String getBeneficiaryName() { return beneficiaryName; }
    public void setBeneficiaryName(String beneficiaryName) { this.beneficiaryName = beneficiaryName; }
    public BeneficiaryType getType() { return type; }
    public void setType(BeneficiaryType type) { this.type = type; }
    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getLastUsedAt() { return lastUsedAt; }
    public void setLastUsedAt(Timestamp lastUsedAt) { this.lastUsedAt = lastUsedAt; }
}
