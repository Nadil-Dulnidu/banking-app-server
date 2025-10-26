package com.bankingapp.root.dto;

import com.bankingapp.root.common.Constants;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Integer id;

    private String accountNumber;

    @NotNull(message = "Account type must not be null")
    private Constants.AccountType accountType;

    @NotNull(message = "Balance must not be null")
    private Double balance;

    private Constants.AccountStatus status;

    private Integer userId;

    private UserDTO user;
}
