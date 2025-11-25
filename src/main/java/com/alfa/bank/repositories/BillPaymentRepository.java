package com.alfa.bank.repositories;

import com.alfa.bank.models.BillPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BillPaymentRepository extends JpaRepository<BillPayment, Long> {
    List<BillPayment> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<BillPayment> findByAccountIdOrderByCreatedAtDesc(Long accountId);
    List<BillPayment> findByStatus(BillPayment.PaymentStatus status);
    List<BillPayment> findByBillerCode(String billerCode);
}
