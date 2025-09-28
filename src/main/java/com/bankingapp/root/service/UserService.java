package com.bankingapp.root.service;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.CustomerAddressDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.entity.CustomerEntity;
import com.bankingapp.root.entity.EmployeeEntity;
import com.bankingapp.root.entity.UserEntity;
import com.bankingapp.root.exception.UserNotFoundException;
import com.bankingapp.root.mapper.CustomerAddressEntityDTOMapper;
import com.bankingapp.root.mapper.UserDTOEntityMapper;
import com.bankingapp.root.repository.AddressRepository;
import com.bankingapp.root.repository.CustomerRepository;
import com.bankingapp.root.repository.EmployeeRepository;
import com.bankingapp.root.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository,
                       CustomerRepository customerRepository,
                       EmployeeRepository employeeRepository,
                       AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDTO updateUser(final UserDTO user) {
        if (Objects.isNull(user))
            throw new IllegalArgumentException("User data must not be null.");
        final UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        if (user.getUserRole().equals(Constants.UserRoles.CUSTOMER)) {
            final CustomerEntity existingCustomer = customerRepository.findCustomerByCustomer_Id(user.getId())
                    .orElseThrow(() -> new UserNotFoundException("Customer not found."));
            existingCustomer.setContactNumber(user.getPhone());
            existingCustomer.setDateOfBirth(user.getBirthDate());
            customerRepository.save(existingCustomer);
        } else {
            final EmployeeEntity existingEmployee = employeeRepository.findCustomerByEmployee_Id(user.getId())
                    .orElseThrow(() -> new UserNotFoundException("Employee not found."));
            existingEmployee.setContactNumber(user.getPhone());
            employeeRepository.save(existingEmployee);
        }
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        final UserEntity savedEntity = userRepository.save(existingUser);
        return UserDTOEntityMapper.map(savedEntity);
    }

    public UserDTO getUserById(Integer id) {
        if (Objects.isNull(id))
            throw new IllegalArgumentException("username must not be null.");
        final UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        final UserDTO userDTO = UserDTOEntityMapper.map(user);
        if (user.getUserRole().equals(Constants.UserRoles.CUSTOMER)) {
            final CustomerEntity existingCustomer = customerRepository.findCustomerByCustomer_Id(user.getId())
                    .orElseThrow(() -> new UserNotFoundException("Customer not found."));
            final List<CustomerAddressDTO> existingAddresses = addressRepository
                    .findByCustomerId(existingCustomer.getId())
                    .stream()
                    .map(CustomerAddressEntityDTOMapper::map)
                    .toList();
            userDTO.setAddresses(existingAddresses);
            userDTO.setBirthDate(existingCustomer.getDateOfBirth());
            userDTO.setPhone(existingCustomer.getContactNumber());
        } else {
            final EmployeeEntity existingEmployee = employeeRepository.findCustomerByEmployee_Id(user.getId())
                    .orElseThrow(() -> new UserNotFoundException("Employee not found."));
            userDTO.setPhone(existingEmployee.getContactNumber());
        }
        return userDTO;
    }

    public UserDTO getUserByUsername(final String username) {
        if (Objects.isNull(username) || username.isEmpty())
            throw new IllegalArgumentException("username must not be null.");
        final UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        final UserDTO userDTO = UserDTOEntityMapper.map(user);
        if (user.getUserRole().equals(Constants.UserRoles.CUSTOMER)) {
            final CustomerEntity existingCustomer = customerRepository.findCustomerByCustomer_Id(user.getId())
                    .orElseThrow(() -> new UserNotFoundException("Customer not found."));
            final List<CustomerAddressDTO> existingAddresses = addressRepository
                    .findByCustomerId(existingCustomer.getId())
                    .stream()
                    .map(CustomerAddressEntityDTOMapper::map)
                    .toList();
            userDTO.setAddresses(existingAddresses);
            userDTO.setBirthDate(existingCustomer.getDateOfBirth());
            userDTO.setPhone(existingCustomer.getContactNumber());
        } else {
            final EmployeeEntity existingEmployee = employeeRepository.findCustomerByEmployee_Id(user.getId())
                    .orElseThrow(() -> new UserNotFoundException("Employee not found."));
            userDTO.setPhone(existingEmployee.getContactNumber());
        }
        return userDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDTO deleteUserById(Integer id) {
        if (Objects.isNull(id))
            throw new IllegalArgumentException("User data must not be null.");
        final UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));
        userRepository.delete(user);
        return UserDTOEntityMapper.map(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDTO deleteUserByUsername(String username) {
        if (Objects.isNull(username) || username.isEmpty())
            throw new IllegalArgumentException("User data must not be null.");
        final UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));
        userRepository.delete(user);
        return UserDTOEntityMapper.map(user);
    }

    public List<UserDTO> getAllUsers() {
        final List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(userEntity -> {
                    final UserDTO userDTO = UserDTOEntityMapper.map(userEntity);
                    if (userEntity.getUserRole().equals(Constants.UserRoles.CUSTOMER)) {
                        final CustomerEntity existingCustomer = customerRepository.findCustomerByCustomer_Id(userEntity.getId())
                                .orElseThrow(() -> new UserNotFoundException("Customer not found."));
                        final List<CustomerAddressDTO> existingAddresses = addressRepository
                                .findByCustomerId(existingCustomer.getId())
                                .stream()
                                .map(CustomerAddressEntityDTOMapper::map)
                                .toList();
                        userDTO.setAddresses(existingAddresses);
                        userDTO.setBirthDate(existingCustomer.getDateOfBirth());
                        userDTO.setPhone(existingCustomer.getContactNumber());
                    } else {
                        final EmployeeEntity existingEmployee = employeeRepository.findCustomerByEmployee_Id(userEntity.getId())
                                .orElseThrow(() -> new UserNotFoundException("Employee not found."));
                        userDTO.setPhone(existingEmployee.getContactNumber());
                    }
                    return userDTO;
                })
                .collect(Collectors.toList());
    }

    public UserDTO updateUserRole(Constants.UserRoles userRole) {
        return null;
    }
}
