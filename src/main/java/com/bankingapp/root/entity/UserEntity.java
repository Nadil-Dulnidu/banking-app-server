package com.bankingapp.root.entity;

import com.bankingapp.root.common.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @NotBlank(message = "username must not be blank")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotBlank(message = "password must not be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "email must not be blank")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "address must not be blank")
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "phone must not be blank")
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull(message = "role must not be null")
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Constants.UserRoles userRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<AccountEntity> accounts;
}
