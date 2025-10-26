package com.bankingapp.root.dto;

import com.bankingapp.root.common.Constants;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanApplicationDTO {
    private Integer id;

    @NotNull(message = "Loan type must not be null")
    private Constants.LoanType loanType;

    @NotNull(message = "Amount must not be null")
    private Double amount;

    @NotNull(message = "Term months must not be null")
    private Integer termMonths;

    @NotNull(message = "Interest rate must not be null")
    private String purpose;

    @NotNull(message = "Annual income must not be null")
    private Double annualIncome;

    private Constants.LoanStatus status;

    private LocalDateTime createdAt;

    private Integer accountId;
}
