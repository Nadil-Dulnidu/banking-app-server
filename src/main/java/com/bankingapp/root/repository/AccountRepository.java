package com.bankingapp.root.repository;

import com.bankingapp.root.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    List<AccountEntity> findByUser_Username(String username);

    Optional<AccountEntity> findByAccountNumber(String accountNumber);
}
