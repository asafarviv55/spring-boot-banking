package com.alfa.bank.repositories;

import com.alfa.bank.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByUserId(Long userId);
    List<Account> findByUserIdAndStatus(Long userId, Account.AccountStatus status);
    List<Account> findByType(Account.AccountType type);
    List<Account> findByStatus(Account.AccountStatus status);

    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.user.id = :userId AND a.status = 'ACTIVE'")
    BigDecimal getTotalBalanceByUser(@Param("userId") Long userId);
}
