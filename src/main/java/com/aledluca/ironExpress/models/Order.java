package com.aledluca.ironExpress.models;

import com.aledluca.ironExpress.enums.OrderStatusValues;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;
    @PastOrPresent
    private LocalDate date;
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatusValues orderStatus;
    private Double total;
    private String cardNumber;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
    private Customer customer;
    @OneToMany
    private List<CartItem> ordercartItems = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
    private Address address;


}
