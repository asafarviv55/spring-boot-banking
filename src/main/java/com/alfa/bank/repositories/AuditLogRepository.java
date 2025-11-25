package com.alfa.bank.repositories;

import com.alfa.bank.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<AuditLog> findByAction(AuditLog.ActionType action);
    List<AuditLog> findByStatus(AuditLog.AuditStatus status);
}
