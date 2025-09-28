package com.bankingapp.root.repository;

import com.bankingapp.root.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    Optional<EmployeeEntity> findCustomerByEmployee_Id(Integer employeeId);
}
