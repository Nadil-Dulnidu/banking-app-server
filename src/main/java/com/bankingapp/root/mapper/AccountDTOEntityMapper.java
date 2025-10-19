package com.bankingapp.root.mapper;

import com.bankingapp.root.dto.AccountDTO;
import com.bankingapp.root.entity.AccountEntity;

public class AccountDTOEntityMapper {

    public static AccountDTO map(final AccountEntity accountEntity) {
        if (accountEntity == null)
            throw new IllegalArgumentException("AccountEntity cannot be null");
        final AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(accountEntity.getId());
        accountDTO.setAccountNumber(accountEntity.getAccountNumber());
        accountDTO.setAccountType(accountEntity.getAccountType());
        accountDTO.setBalance(accountEntity.getBalance());
        accountDTO.setStatus(accountEntity.getStatus());
        accountDTO.setAccountType(accountEntity.getAccountType());
        if(accountEntity.getUser() == null)
            throw new IllegalArgumentException("UserEntity in AccountEntity cannot be null");
        accountDTO.setUserId(accountEntity.getUser().getId());
        return accountDTO;
    }

    public static AccountEntity map(final AccountDTO accountDTO) {
        if (accountDTO == null)
            throw new IllegalArgumentException("AccountDTO cannot be null");
        final AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountDTO.getId());
        accountEntity.setAccountNumber(accountDTO.getAccountNumber());
        accountEntity.setAccountType(accountDTO.getAccountType());
        accountEntity.setBalance(accountDTO.getBalance());
        accountEntity.setStatus(accountDTO.getStatus());
        accountEntity.setAccountType(accountDTO.getAccountType());
        return accountEntity;
    }
}
