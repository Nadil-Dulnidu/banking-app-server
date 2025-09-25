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
}
