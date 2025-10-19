package com.bankingapp.root.mapper;

import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.entity.UserEntity;

import java.util.Objects;

public class UserDTOEntityMapper {
    public static UserDTO map(final UserEntity userEntity) {
        if(Objects.isNull(userEntity))
            throw new IllegalArgumentException("userEntity must not be null");
        final UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setUserRole(userEntity.getUserRole());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setAddress(userEntity.getAddress());
        return userDTO;
    }

    public static UserEntity map(final UserDTO userDTO) {
        if(Objects.isNull(userDTO))
            throw new IllegalArgumentException("userDTO must not be null");
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setUserRole(userDTO.getUserRole());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setAddress(userDTO.getAddress());
        return userEntity;
    }
}
