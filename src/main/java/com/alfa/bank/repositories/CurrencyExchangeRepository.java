package com.alfa.bank.repositories;

import com.alfa.bank.models.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
    List<CurrencyExchange> findByUserIdOrderByCreatedAtDesc(Long userId);
}
