package com.bankingapp.root.dto;

import com.bankingapp.root.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    @JsonProperty("account_id")
    private Integer id;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_type")
    private Constants.AccountType accountType;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("account_status")
    private Constants.AccountStatus status;

    @JsonProperty("user_id")
    private Integer userId;
}
