package com.aledluca.ironExpress.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sellerId;
    @NotNull(message="First name cannot be null")
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
    @Email
    @Column(unique = true)
    private String email;
    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can have A-Z, a-z, 0-9, or special characters !@#$%^&*_")
    private String password;
    @PastOrPresent
    private LocalDateTime createdOn;
    @OneToMany
    @JsonIgnore
    private List<Product> product;


    public Seller() {
    }

    public Seller(String firstName, String lastName, String contactNumber, String email, String password, LocalDateTime createdOn, List<Product> product) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.createdOn = createdOn;
        this.product = product;
    }

    public Seller(Integer sellerId, String firstName, String lastName, String contactNumber, String email, String password, LocalDateTime createdOn, List<Product> product) {
        this.sellerId = sellerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.createdOn = createdOn;
        this.product = product;
    }

    public Seller(String firstName, String lastName, String contactNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(sellerId, seller.sellerId) && Objects.equals(firstName, seller.firstName) && Objects.equals(lastName, seller.lastName) && Objects.equals(contactNumber, seller.contactNumber) && Objects.equals(password, seller.password) && Objects.equals(email, seller.email) && Objects.equals(product, seller.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerId, firstName, lastName, contactNumber, password, email, product);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerId=" + sellerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", product=" + product +
                '}';
    }
}
