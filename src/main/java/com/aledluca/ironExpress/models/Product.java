package com.aledluca.ironExpress.models;

import com.aledluca.ironExpress.enums.Category;
import com.aledluca.ironExpress.enums.ProductStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Objects;

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


    public Product() {
    }

    public Product(String productName, Double price, String description, String manufacturer, Integer quantity, Category category, ProductStatus status, Seller seller) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.category = category;
        this.status = status;
        this.seller = seller;
    }

    public Product(Integer productId, String productName, Double price, String description, String manufacturer, Integer quantity, Category category, ProductStatus status, Seller seller) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.category = category;
        this.status = status;
        this.seller = seller;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(productName, product.productName) && Objects.equals(price, product.price) && Objects.equals(description, product.description) && Objects.equals(manufacturer, product.manufacturer) && Objects.equals(quantity, product.quantity) && category == product.category && status == product.status && Objects.equals(seller, product.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, price, description, manufacturer, quantity, category, status, seller);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", quantity=" + quantity +
                ", category=" + category +
                ", status=" + status +
                ", seller=" + seller +
                '}';
    }
}
