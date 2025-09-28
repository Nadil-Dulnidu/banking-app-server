package com.bankingapp.root.common;

public class Constants {

    public static final String ADMIN = "hasAuthority('ADMIN')";
    public static final String CUSTOMER = "hasAuthority('CUSTOMER')";
    public static final String BRANCH_MANAGER = "hasAuthority('BRANCH_MANAGER')";
    public static final String SUPPORT_STAFF = "hasAuthority('SUPPORT_STAFF')";
    public static final String AUDITOR = "hasAuthority('AUDITOR')";
    public static final String CUSTOMER_MANAGER = "hasAuthority('BANK_EMPLOYEE')";
    public static final String ADMIN_OR_CUSTOMER = "hasAnyAuthority('ADMIN', 'CUSTOMER')";
    public static final String STAFF_OR_CUSTOMER = "hasAnyAuthority('SUPPORT_STAFF', 'CUSTOMER')";

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
}
