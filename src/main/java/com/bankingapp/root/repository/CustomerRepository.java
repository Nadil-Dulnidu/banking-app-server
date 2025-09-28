package com.bankingapp.root.repository;

import com.bankingapp.root.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    Optional<CustomerEntity> findCustomerByCustomer_Id(Integer customerId);
}
