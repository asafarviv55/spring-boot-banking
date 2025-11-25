package com.alfa.bank.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "scheduled_payments")
public class ScheduledPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @Column(precision = 19, scale = 4)
    private BigDecimal amount;

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate nextExecutionDate;
    private Integer executionDay;
    private Integer totalExecutions;
    private Integer completedExecutions;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    private Timestamp createdAt;
    private Timestamp lastExecutedAt;

    public enum PaymentType {
        TRANSFER, BILL_PAYMENT, LOAN_PAYMENT
    }

    public enum Frequency {
        ONCE, DAILY, WEEKLY, BIWEEKLY, MONTHLY, QUARTERLY, YEARLY
    }

    public enum ScheduleStatus {
        ACTIVE, PAUSED, COMPLETED, CANCELLED, FAILED
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        if (status == null) status = ScheduleStatus.ACTIVE;
        completedExecutions = 0;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Account getFromAccount() { return fromAccount; }
    public void setFromAccount(Account fromAccount) { this.fromAccount = fromAccount; }
    public Beneficiary getBeneficiary() { return beneficiary; }
    public void setBeneficiary(Beneficiary beneficiary) { this.beneficiary = beneficiary; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public PaymentType getType() { return type; }
    public void setType(PaymentType type) { this.type = type; }
    public Frequency getFrequency() { return frequency; }
    public void setFrequency(Frequency frequency) { this.frequency = frequency; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public LocalDate getNextExecutionDate() { return nextExecutionDate; }
    public void setNextExecutionDate(LocalDate nextExecutionDate) { this.nextExecutionDate = nextExecutionDate; }
    public Integer getExecutionDay() { return executionDay; }
    public void setExecutionDay(Integer executionDay) { this.executionDay = executionDay; }
    public Integer getTotalExecutions() { return totalExecutions; }
    public void setTotalExecutions(Integer totalExecutions) { this.totalExecutions = totalExecutions; }
    public Integer getCompletedExecutions() { return completedExecutions; }
    public void setCompletedExecutions(Integer completedExecutions) { this.completedExecutions = completedExecutions; }
    public ScheduleStatus getStatus() { return status; }
    public void setStatus(ScheduleStatus status) { this.status = status; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getLastExecutedAt() { return lastExecutedAt; }
    public void setLastExecutedAt(Timestamp lastExecutedAt) { this.lastExecutedAt = lastExecutedAt; }
}
