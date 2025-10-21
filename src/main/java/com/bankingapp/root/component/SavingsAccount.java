package com.bankingapp.root.component;

import org.springframework.stereotype.Component;

@Component
class SavingsAccount implements Account {

    @Override
    public double getInitialBalance() {
        return 1000.00;
    }
}