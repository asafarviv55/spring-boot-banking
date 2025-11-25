package com.alfa.bank.repositories;

import com.alfa.bank.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardNumber(String cardNumber);
    List<Card> findByUserId(Long userId);
    List<Card> findByAccountId(Long accountId);
    List<Card> findByUserIdAndStatus(Long userId, Card.CardStatus status);
    List<Card> findByExpiryDateBefore(LocalDate date);
}
