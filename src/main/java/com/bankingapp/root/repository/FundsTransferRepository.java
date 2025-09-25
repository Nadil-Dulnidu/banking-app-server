package com.bankingapp.root.repository;

import com.bankingapp.root.entity.FundsTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundsTransferRepository extends JpaRepository<FundsTransferEntity,Integer> {

    List<FundsTransferEntity> findByFromAccount(String fromAccount);
}
