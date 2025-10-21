package com.bankingapp.root.component;

import org.springframework.stereotype.Component;

@Component
public class BusinessAccount implements Account {
    @Override
    public double getInitialBalance() {
        return 10000.00;
    }
}
