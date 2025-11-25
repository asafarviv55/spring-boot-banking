package com.alfa.bank.repositories;

import com.alfa.bank.models.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {
    List<Statement> findByAccountIdOrderByPeriodEndDesc(Long accountId);
}
