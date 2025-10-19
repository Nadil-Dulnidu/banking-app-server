package com.bankingapp.root.repository;

import com.bankingapp.root.entity.BillPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillPaymentRepository extends JpaRepository<BillPaymentEntity, Integer> {

    List<BillPaymentEntity> findAllByAccount_AccountNumber(String accountNumber);

    List<BillPaymentEntity> findAllByAccount_Id(Integer accountId);
}
