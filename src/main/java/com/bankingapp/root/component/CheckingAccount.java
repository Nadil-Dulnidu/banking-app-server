package com.bankingapp.root.component;

import org.springframework.stereotype.Component;

@Component
class CheckingAccount implements Account {

    @Override
    public double getInitialBalance() {
        return 2000.00;
    }
}
