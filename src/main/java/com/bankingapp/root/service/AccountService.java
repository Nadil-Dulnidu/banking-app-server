package com.bankingapp.root.service;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.AccountDTO;
import com.bankingapp.root.entity.AccountEntity;
import com.bankingapp.root.entity.UserEntity;
import com.bankingapp.root.exception.AccountException;
import com.bankingapp.root.mapper.AccountDTOEntityMapper;
import com.bankingapp.root.repository.AccountRepository;
import com.bankingapp.root.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(final AccountRepository accountRepository,
    UserRepository userRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public void createAccount(AccountDTO account, String username) {
        if (account == null)
            throw new AccountException("User ID is required to create an account");
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        AccountEntity accountEntity = AccountDTOEntityMapper.map(account);
        accountEntity.setUser(userEntity);
        String accountNumber = "AC" + System.currentTimeMillis();
        accountEntity.setAccountNumber(accountNumber);
        accountEntity.setStatus(Constants.AccountStatus.ACTIVE);
        AccountEntity savedAccount = accountRepository.save(accountEntity);
        AccountDTOEntityMapper.map(savedAccount);
    }

    public AccountDTO getAccount(Integer accountId) {
        if (accountId == null)
            throw new AccountException("Account ID is required");
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountDTOEntityMapper.map(accountEntity);
    }

    public List<AccountDTO> getAllAccountsByUser(String username) {
        List<AccountDTO> accounts = accountRepository.findByUser_Username(username)
                .stream()
                .map(AccountDTOEntityMapper::map)
                .toList();
        return accounts;
    }

    public List<AccountDTO> getAllAccounts() {
        List<AccountDTO> accounts = accountRepository.findAll()
                .stream()
                .map(AccountDTOEntityMapper::map)
                .toList();
        return accounts;
    }

    public AccountDTO updateStatus(Integer id, Constants.AccountStatus status) {
        AccountEntity acc = accountRepository.findById(id)
                .orElseThrow(() -> new AccountException("Account not found"));
        acc.setStatus(status);
        AccountEntity updatedAcc = accountRepository.save(acc);
        return AccountDTOEntityMapper.map(updatedAcc);
    }

    public AccountDTO deleteAccount(Integer id) {
        AccountEntity acc = accountRepository.findById(id)
                .orElseThrow(() -> new AccountException("Account not found"));
        if (acc.getBalance() != 0)
            throw new RuntimeException("Account balance must be zero before closing");
        acc.setStatus(Constants.AccountStatus.CLOSED);
        accountRepository.save(acc);
        return AccountDTOEntityMapper.map(acc);
    }
}
