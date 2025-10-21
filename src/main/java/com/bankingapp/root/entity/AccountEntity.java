package com.bankingapp.root.entity;

import com.bankingapp.root.common.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Integer id;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Constants.AccountType accountType;

    @Column(name = "balance", nullable = false, columnDefinition = "DECIMAL(15,2) CHECK (balance >= 1000.00)")
    private Double balance;

    @Column(name = "account_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Constants.AccountStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL, orphanRemoval = true)
    List<FundsTransferEntity> fundsTransfers;

    @OneToMany(mappedBy = "account",orphanRemoval = true,cascade = CascadeType.ALL)
    List<BillPaymentEntity> billPayments;
}
