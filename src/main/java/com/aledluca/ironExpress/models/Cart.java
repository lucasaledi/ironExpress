package com.aledluca.ironExpress.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;


@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    private Double cartTotal;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Customer customer;
}
