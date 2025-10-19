package com.bankingapp.root.common;

public class Constants {

    public enum TransferType {
        INTRA_BANK,
        INTER_BANK
    }

    public enum TransferStatus {
        PENDING,
        COMPLETED,
        FAILED,
        CANCELLED
    }

    public enum UserRoles {
        ADMIN,
        CUSTOMER,
        BANK_EMPLOYEE,
        SUPPORT_STAFF,
        AUDITOR,
        BRANCH_MANAGER,
    }

    public enum AccountStatus {
        ACTIVE,
        SUSPENDED,
        CLOSED
    }

    public enum AccountType {
        SAVINGS,
        CHECKING,
        BUSINESS,
        LOAN
    }

    public enum PaymentFrequency {
        ONE_TIME,
        MONTHLY
    }
}
