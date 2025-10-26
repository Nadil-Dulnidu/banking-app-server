package com.bankingapp.root.entity;

import com.bankingapp.root.common.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "funds_transfer")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FundsTransferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @Column(name = "to_account", nullable = false)
    @NotBlank(message = "To account must not be null")
    private String toAccount;

    @Column(name = "beneficiary_name", nullable = false)
    @NotBlank(message = "Beneficiary name must not be null")
    private String beneficiaryName;

    @Column(name = "amount", nullable = false)
    @NotNull(message = "Amount must not be null")
    private Double amount;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Constants.TransferStatus status;

    @Column(name = "transfer_type", nullable = false)
    @NotNull(message = "Transfer type must not be null")
    @Enumerated(EnumType.STRING)
    private Constants.TransferType transferType;

    @Column(name = "created_at", nullable = false)
    @NotNull(message = "Created at must not be null")
    private LocalDateTime createdAt;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;
}
