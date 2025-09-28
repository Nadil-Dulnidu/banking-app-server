package com.bankingapp.root.repository;

import com.bankingapp.root.entity.CustomerAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<CustomerAddressEntity, Integer> {

    List<CustomerAddressEntity> findByCustomerId(Integer id);
}
