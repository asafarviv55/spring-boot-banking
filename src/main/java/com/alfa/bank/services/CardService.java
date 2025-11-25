package com.alfa.bank.services;

import com.alfa.bank.models.Account;
import com.alfa.bank.models.Card;
import com.alfa.bank.models.User;
import com.alfa.bank.repositories.AccountRepository;
import com.alfa.bank.repositories.CardRepository;
import com.alfa.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public Card issueCard(Long userId, Long accountId, Card.CardType type, String nameOnCard) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Card card = new Card();
        card.setUser(user);
        card.setAccount(account);
        card.setCardNumber(generateCardNumber());
        card.setType(type);
        card.setNameOnCard(nameOnCard);
        card.setExpiryDate(LocalDate.now().plusYears(3));
        card.setStatus(Card.CardStatus.INACTIVE);

        if (type == Card.CardType.DEBIT) {
            card.setDailyLimit(new BigDecimal("5000"));
            card.setMonthlyLimit(new BigDecimal("50000"));
        } else if (type == Card.CardType.CREDIT) {
            card.setCreditLimit(new BigDecimal("10000"));
        }

        return cardRepository.save(card);
    }

    public Card activateCard(Long cardId, String pin) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setStatus(Card.CardStatus.ACTIVE);
        card.setPin(pin);
        return cardRepository.save(card);
    }

    public Card blockCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setStatus(Card.CardStatus.BLOCKED);
        card.setBlockedAt(new Timestamp(System.currentTimeMillis()));
        return cardRepository.save(card);
    }

    public Card unblockCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setStatus(Card.CardStatus.ACTIVE);
        card.setBlockedAt(null);
        return cardRepository.save(card);
    }

    public Card setLimits(Long cardId, BigDecimal dailyLimit, BigDecimal monthlyLimit) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (dailyLimit != null) card.setDailyLimit(dailyLimit);
        if (monthlyLimit != null) card.setMonthlyLimit(monthlyLimit);

        return cardRepository.save(card);
    }

    public Card toggleContactless(Long cardId, boolean enabled) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setContactless(enabled);
        return cardRepository.save(card);
    }

    public Card toggleInternational(Long cardId, boolean enabled) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setInternational(enabled);
        return cardRepository.save(card);
    }

    public Card toggleOnlinePurchase(Long cardId, boolean enabled) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setOnlinePurchase(enabled);
        return cardRepository.save(card);
    }

    public Card changePin(Long cardId, String oldPin, String newPin) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (!card.getPin().equals(oldPin)) {
            throw new RuntimeException("Invalid PIN");
        }

        card.setPin(newPin);
        return cardRepository.save(card);
    }

    public List<Card> getUserCards(Long userId) {
        return cardRepository.findByUserId(userId);
    }

    public List<Card> getActiveUserCards(Long userId) {
        return cardRepository.findByUserIdAndStatus(userId, Card.CardStatus.ACTIVE);
    }

    public List<Card> getExpiringCards(int daysAhead) {
        return cardRepository.findByExpiryDateBefore(LocalDate.now().plusDays(daysAhead));
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("4"); // Visa prefix
        for (int i = 0; i < 15; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
