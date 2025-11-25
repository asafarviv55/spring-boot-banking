package com.alfa.bank.repositories;

import com.alfa.bank.models.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    List<Beneficiary> findByUserIdAndIsActiveTrue(Long userId);
    List<Beneficiary> findByUserIdAndType(Long userId, Beneficiary.BeneficiaryType type);
    List<Beneficiary> findByUserIdAndIsVerifiedTrue(Long userId);
}
