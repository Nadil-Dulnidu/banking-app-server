package com.bankingapp.root.service;

import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.entity.AccountEntity;
import com.bankingapp.root.entity.UserEntity;
import com.bankingapp.root.exception.UserNotFoundException;
import com.bankingapp.root.mapper.UserDTOEntityMapper;
import com.bankingapp.root.repository.AccountRepository;
import com.bankingapp.root.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUser(final UserDTO user) {
        if (Objects.isNull(user))
            throw new IllegalArgumentException("User data must not be null.");
        final UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAddress(user.getAddress());
        existingUser.setPhone(user.getPhone());
        final UserEntity savedEntity = userRepository.save(existingUser);
        UserDTOEntityMapper.map(savedEntity);
    }


    public UserDTO getUserByUsername(final String username) {
        if (Objects.isNull(username) || username.isEmpty())
            throw new IllegalArgumentException("username must not be null.");
        final UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        return UserDTOEntityMapper.map(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUserById(Integer id) {
        if (Objects.isNull(id))
            throw new IllegalArgumentException("User data must not be null.");
        final UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));
        List<AccountEntity> accounts = accountRepository.findByUser_Username(user.getUsername());
        if (!accounts.isEmpty()) {
            throw new IllegalStateException("Cannot delete user with existing accounts.");
        }
        userRepository.delete(user);
        UserDTOEntityMapper.map(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUserByUsername(String username) {
        if (Objects.isNull(username) || username.isEmpty())
            throw new IllegalArgumentException("User data must not be null.");
        List<AccountEntity> accounts = accountRepository.findByUser_Username(username);
        if (!accounts.isEmpty()) {
            throw new IllegalStateException("Cannot delete user with existing accounts.");
        }
        final UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));
        userRepository.delete(user);
        UserDTOEntityMapper.map(user);
    }

    public List<UserDTO> getAllUsers() {
        final List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(UserDTOEntityMapper::map)
                .collect(Collectors.toList());
    }

}
