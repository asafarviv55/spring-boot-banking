package com.alfa.bank.repositories;

import com.alfa.bank.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionRef(String transactionRef);
    List<Transaction> findByFromAccountIdOrderByCreatedAtDesc(Long accountId);
    List<Transaction> findByToAccountIdOrderByCreatedAtDesc(Long accountId);
    List<Transaction> findByType(Transaction.TransactionType type);
    List<Transaction> findByStatus(Transaction.TransactionStatus status);

    @Query("SELECT t FROM Transaction t WHERE (t.fromAccount.id = :accountId OR t.toAccount.id = :accountId) AND t.createdAt BETWEEN :startDate AND :endDate ORDER BY t.createdAt DESC")
    List<Transaction> findByAccountAndDateRange(@Param("accountId") Long accountId, @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}
