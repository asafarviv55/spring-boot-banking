package com.alfa.bank.repositories;

import com.alfa.bank.models.ScheduledPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPayment, Long> {
    List<ScheduledPayment> findByUserIdAndStatus(Long userId, ScheduledPayment.ScheduleStatus status);
    List<ScheduledPayment> findByFromAccountId(Long accountId);

    @Query("SELECT s FROM ScheduledPayment s WHERE s.status = 'ACTIVE' AND s.nextExecutionDate = :date")
    List<ScheduledPayment> findDuePayments(@Param("date") LocalDate date);
}
