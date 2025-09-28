package com.bankingapp.root.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressDTO {

    @JsonProperty("address_id")
    private Integer addressId;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("address")
    @NotBlank(message = "Address cannot be blank")
    private String address;
}
