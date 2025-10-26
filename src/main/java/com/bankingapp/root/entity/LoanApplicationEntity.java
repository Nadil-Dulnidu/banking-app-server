package com.bankingapp.root.entity;

import com.bankingapp.root.common.Constants;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_application")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @Column(name = "loan_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Constants.LoanType loanType;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "term_months", nullable = false)
    private Integer termMonths;

    @Column(name = "purpose", nullable = false)
    private String purpose;

    @Column(name = "annual_income", nullable = false)
    private Double annualIncome;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Constants.LoanStatus status = Constants.LoanStatus.PENDING;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}

