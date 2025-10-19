package com.bankingapp.root.dto;

import com.bankingapp.root.common.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillPaymentDTO {

    private Integer id;

    private String billerName;

    private Double amount;

    private Constants.PaymentFrequency paymentFrequency;

    private LocalDate nextPaymentDate;

    private Integer accountId;
}
