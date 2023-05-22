package com.aledluca.ironExpress.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerId;
    @NotNull(message = "First name cannot be null")
    @Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in first name")
    private String firstName;
    @NotNull(message = "Last Name cannot be null")
    @Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in last name")
    private String lastName;
    @NotNull(message = "Contact number cannot be null")
    @Column(unique = true)
    @Pattern(regexp = "[6789]{1}[0-9]{9}", message = "Enter valid 10 digit mobile number")
    private String contactNumber;
    @NotNull(message = "Email address cannot be null")
    @Column(unique = true)
    @Email
    private String email;
    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can have A-Z, a-z, 0-9, or special characters !@#$%^&*_")
    private String password;
    private LocalDateTime createdOn;
    @Embedded
    private CreditCard creditCard;
    //  Establishing Address - Customer relationship
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_address_mapping",
            joinColumns = {
                    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
            })
    private Map<String, Address> address = new HashMap<>();
    //  Establishing Customer - Order relationship
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    //	Establishing Customer - Cart relationship
    @OneToOne(cascade = CascadeType.ALL)
    private Cart customerCart;
}
