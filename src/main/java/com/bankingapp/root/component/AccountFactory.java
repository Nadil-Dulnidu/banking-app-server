package com.bankingapp.root.component;

import com.bankingapp.root.common.Constants;
import org.springframework.stereotype.Component;

@Component
public class AccountFactory {

    public Account createAccount(Constants.AccountType accountType) {
        if (accountType == null) {
            throw new IllegalArgumentException("Account type cannot be null");
        }
        return switch (accountType) {
            case SAVINGS -> new SavingsAccount();
            case CHECKING -> new CheckingAccount();
            case BUSINESS -> new BusinessAccount();
        };
    }
}
