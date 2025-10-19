package com.bankingapp.root.entity;

import com.bankingapp.root.common.Constants;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "bill_payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Integer id;

    @Column(name = "biller_name",nullable = false)
    private String billerName;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "payment_frequency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Constants.PaymentFrequency paymentFrequency;

    @Column(name = "next_payment")
    private LocalDate nextPaymentDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;
}