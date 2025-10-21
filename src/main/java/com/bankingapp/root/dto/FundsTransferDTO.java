package com.bankingapp.root.dto;

import com.bankingapp.root.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundsTransferDTO {
    private Integer id;

    @NotBlank(message = "From account must not be null")
    private String fromAccount;

    @NotBlank(message = "To account must not be null")
    private String toAccount;

    @NotBlank(message = "Beneficiary Name must not be null")
    private String beneficiaryName;

    @NotNull(message = "Amount must not be null")
    private Double amount;

    private String description;

    private Constants.TransferStatus status;

    @NotNull(message = "Transfer type must not be null")
    private Constants.TransferType transferType;

    private LocalDateTime createdAt;

    private LocalDateTime scheduledAt;
}
