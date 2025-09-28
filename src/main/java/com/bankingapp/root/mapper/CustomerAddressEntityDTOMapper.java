package com.bankingapp.root.mapper;

import com.bankingapp.root.dto.CustomerAddressDTO;
import com.bankingapp.root.entity.CustomerAddressEntity;

import java.util.Objects;

public class CustomerAddressEntityDTOMapper {
    public static CustomerAddressEntity map(final CustomerAddressDTO customerAddressDTO) {
        if (Objects.isNull(customerAddressDTO))
            throw new IllegalArgumentException("customerAddressDTO must not be null");
        final CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
        customerAddressEntity.setAddress(customerAddressDTO.getAddress());
        customerAddressEntity.setId(customerAddressDTO.getAddressId());
        return customerAddressEntity;
    }

    public static CustomerAddressDTO map(final CustomerAddressEntity customerAddressEntity) {
        if (Objects.isNull(customerAddressEntity))
            throw new IllegalArgumentException("customerAddressEntity must not be null");
        final CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO();
        customerAddressDTO.setAddress(customerAddressEntity.getAddress());
        customerAddressDTO.setAddressId(customerAddressEntity.getId());
        if (!Objects.isNull(customerAddressEntity.getCustomer()))
            customerAddressDTO.setUserId(customerAddressEntity.getCustomer().getId());
        return customerAddressDTO;
    }
}
