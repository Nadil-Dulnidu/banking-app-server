package com.bankingapp.root.dto;

import com.bankingapp.root.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;

    @NotBlank(message = "Username can not be blank")
    private String username;

    @NotBlank(message = "Password can not be blank")
    private String password;

    @NotBlank(message = "First name can not be null")
    private String firstName;

    @NotBlank(message = "Last name can not be null")
    private String lastName;

    @NotBlank(message = "Email can not be blank")
    @Email(message = "Invalid email format")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Address can not be blank")
    private String address;

    @NotBlank(message = "Phone can not be blank")
    private String phone;

    @NotNull(message = "User role can not be null")
    private Constants.UserRoles userRole;
}

