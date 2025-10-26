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
        BUSINESS
    }

    public enum PaymentFrequency {
        ONE_TIME,
        MONTHLY
    }

    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED
    }

    public enum NotificationType {
        ACCOUNT,
        TRANSACTION,
        BILLING,
        SECURITY,
        ANNOUNCEMENT
    }

    public enum LoanType {
        HOME,
        VEHICLE,
        PERSONAL
    }

    public enum LoanStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}
