package com.bankingapp.root.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity customer;

    @Column(name = "date_of_birthday")
    @NotNull(message = "Birthday cannot be null")
    private Date dateOfBirth;

    @Column(name = "contact_number")
    @NotBlank(message = "Contact number cannot be blank")
    private String contactNumber;

    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CustomerAddressEntity> addresses;
}
