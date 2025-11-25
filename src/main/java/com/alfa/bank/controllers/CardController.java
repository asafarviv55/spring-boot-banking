package com.alfa.bank.controllers;

import com.alfa.bank.models.Card;
import com.alfa.bank.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<Card> issueCard(
            @RequestParam Long userId,
            @RequestParam Long accountId,
            @RequestParam Card.CardType type,
            @RequestParam String nameOnCard) {
        return ResponseEntity.ok(cardService.issueCard(userId, accountId, type, nameOnCard));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<Card> activate(@PathVariable Long id, @RequestParam String pin) {
        return ResponseEntity.ok(cardService.activateCard(id, pin));
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<Card> block(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.blockCard(id));
    }

    @PostMapping("/{id}/unblock")
    public ResponseEntity<Card> unblock(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.unblockCard(id));
    }

    @PutMapping("/{id}/limits")
    public ResponseEntity<Card> setLimits(
            @PathVariable Long id,
            @RequestParam(required = false) BigDecimal dailyLimit,
            @RequestParam(required = false) BigDecimal monthlyLimit) {
        return ResponseEntity.ok(cardService.setLimits(id, dailyLimit, monthlyLimit));
    }

    @PutMapping("/{id}/contactless")
    public ResponseEntity<Card> toggleContactless(@PathVariable Long id, @RequestParam boolean enabled) {
        return ResponseEntity.ok(cardService.toggleContactless(id, enabled));
    }

    @PutMapping("/{id}/international")
    public ResponseEntity<Card> toggleInternational(@PathVariable Long id, @RequestParam boolean enabled) {
        return ResponseEntity.ok(cardService.toggleInternational(id, enabled));
    }

    @PutMapping("/{id}/online-purchase")
    public ResponseEntity<Card> toggleOnlinePurchase(@PathVariable Long id, @RequestParam boolean enabled) {
        return ResponseEntity.ok(cardService.toggleOnlinePurchase(id, enabled));
    }

    @PutMapping("/{id}/pin")
    public ResponseEntity<Card> changePin(
            @PathVariable Long id,
            @RequestParam String oldPin,
            @RequestParam String newPin) {
        return ResponseEntity.ok(cardService.changePin(id, oldPin, newPin));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Card>> getUserCards(@PathVariable Long userId) {
        return ResponseEntity.ok(cardService.getUserCards(userId));
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<Card>> getActiveCards(@PathVariable Long userId) {
        return ResponseEntity.ok(cardService.getActiveUserCards(userId));
    }

    @GetMapping("/expiring")
    public ResponseEntity<List<Card>> getExpiringCards(@RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(cardService.getExpiringCards(days));
    }
}
