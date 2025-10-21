package com.bankingapp.root.dto;

import com.bankingapp.root.common.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillPaymentDTO {
    private Integer id;

    @NotBlank(message = "Biller name must not be null")
    private String billerName;

    @NotNull(message = "Amount must not be null")
    private Double amount;

    @NotNull(message = "Payment frequency must not be null")
    private Constants.PaymentFrequency paymentFrequency;

    @NotBlank(message = "Next payment date must not be null")
    private String accountNumber;

    private LocalDateTime paymentDate;

    private Constants.PaymentStatus paymentStatus;
}
