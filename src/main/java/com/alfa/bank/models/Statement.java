package com.alfa.bank.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "statements")
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private LocalDate periodStart;
    private LocalDate periodEnd;

    @Column(precision = 19, scale = 4)
    private BigDecimal openingBalance;

    @Column(precision = 19, scale = 4)
    private BigDecimal closingBalance;

    @Column(precision = 19, scale = 4)
    private BigDecimal totalCredits;

    @Column(precision = 19, scale = 4)
    private BigDecimal totalDebits;

    private Integer transactionCount;
    private String statementRef;
    private String pdfUrl;
    private boolean isViewed;
    private Timestamp generatedAt;
    private Timestamp viewedAt;

    @PrePersist
    protected void onCreate() {
        generatedAt = new Timestamp(System.currentTimeMillis());
        isViewed = false;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }
    public LocalDate getPeriodStart() { return periodStart; }
    public void setPeriodStart(LocalDate periodStart) { this.periodStart = periodStart; }
    public LocalDate getPeriodEnd() { return periodEnd; }
    public void setPeriodEnd(LocalDate periodEnd) { this.periodEnd = periodEnd; }
    public BigDecimal getOpeningBalance() { return openingBalance; }
    public void setOpeningBalance(BigDecimal openingBalance) { this.openingBalance = openingBalance; }
    public BigDecimal getClosingBalance() { return closingBalance; }
    public void setClosingBalance(BigDecimal closingBalance) { this.closingBalance = closingBalance; }
    public BigDecimal getTotalCredits() { return totalCredits; }
    public void setTotalCredits(BigDecimal totalCredits) { this.totalCredits = totalCredits; }
    public BigDecimal getTotalDebits() { return totalDebits; }
    public void setTotalDebits(BigDecimal totalDebits) { this.totalDebits = totalDebits; }
    public Integer getTransactionCount() { return transactionCount; }
    public void setTransactionCount(Integer transactionCount) { this.transactionCount = transactionCount; }
    public String getStatementRef() { return statementRef; }
    public void setStatementRef(String statementRef) { this.statementRef = statementRef; }
    public String getPdfUrl() { return pdfUrl; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }
    public boolean isViewed() { return isViewed; }
    public void setViewed(boolean viewed) { isViewed = viewed; }
    public Timestamp getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(Timestamp generatedAt) { this.generatedAt = generatedAt; }
    public Timestamp getViewedAt() { return viewedAt; }
    public void setViewedAt(Timestamp viewedAt) { this.viewedAt = viewedAt; }
}
