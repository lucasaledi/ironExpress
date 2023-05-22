package com.aledluca.ironExpress.models;

import com.aledluca.ironExpress.enums.Category;
import com.aledluca.ironExpress.enums.ProductStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;
    @NotNull(message = "Product name cannot be null")
    @Size(min = 3, max = 30, message = "Product name must be between 3-30 characters long")
    private String productName;
    @NotNull(message = "Product price cannot be null")
    @DecimalMin(value = "0.00")
    private Double price;
    private String description;
    @NotNull(message = "Product manufacturer cannot be null")
    @Size(min = 3, max = 30, message = "Product manufacturer must be between 3-30 characters long")
    private String manufacturer;
    @NotNull(message = "Product quantity cannot be null")
    @Min(value = 0)
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

//	@ManyToMany(cascade = CascadeType.ALL)
//	private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Seller seller;

//	@ManyToMany
//	@JsonIgnore
//	private List<Cart> productCarts = new ArrayList<>();
}
