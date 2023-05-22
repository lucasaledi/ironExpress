package com.aledluca.ironExpress.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartItemId;
    @OneToOne
    @JsonIgnoreProperties(value={
            "productId",
            "seller",
            "quantity"
    })
    private Product cartProduct;
    private Integer cartItemQuantity;
}
