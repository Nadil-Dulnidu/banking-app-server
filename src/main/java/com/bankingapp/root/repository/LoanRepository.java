package com.bankingapp.root.repository;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.entity.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanApplicationEntity, Integer> {
    List<LoanApplicationEntity> findByAccount_User_Username(String username);
    List<LoanApplicationEntity> findByStatus(Constants.LoanStatus status);
}
