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
    @JsonProperty("transfer_id")
    private Integer id;

    @JsonProperty("from_account")
    @NotBlank(message = "From account must not be null")
    private String fromAccount;

    @JsonProperty("to_account")
    @NotBlank(message = "To account must not be null")
    private String toAccount;

    @JsonProperty("beneficiary_name")
    @NotBlank(message = "Beneficiary Name must not be null")
    private String beneficiaryName;

    @JsonProperty("amount")
    @NotNull(message = "Amount must not be null")
    private Double amount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private Constants.TransferStatus status;

    @JsonProperty("transfer_type")
    @NotNull(message = "Transfer type must not be null")
    private Constants.TransferType transferType;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("scheduled_at")
    private LocalDateTime scheduledAt;


}
