package com.aledluca.ironExpress.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.*;

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
    @PastOrPresent
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


    public Customer() {
    }

    public Customer(String firstName, String lastName, String contactNumber, String email, String password, LocalDateTime createdOn, CreditCard creditCard, Map<String, Address> address, List<Order> orders, Cart customerCart) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.createdOn = createdOn;
        this.creditCard = creditCard;
        this.address = address;
        this.orders = orders;
        this.customerCart = customerCart;
    }

    public Customer(Integer customerId, String firstName, String lastName, String contactNumber, String email, String password, LocalDateTime createdOn, CreditCard creditCard, Map<String, Address> address, List<Order> orders, Cart customerCart) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.createdOn = createdOn;
        this.creditCard = creditCard;
        this.address = address;
        this.orders = orders;
        this.customerCart = customerCart;
    }

    public Customer(String firstName, String lastName, String contactNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Map<String, Address> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Address> address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Cart getCustomerCart() {
        return customerCart;
    }

    public void setCustomerCart(Cart customerCart) {
        this.customerCart = customerCart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId) && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(contactNumber, customer.contactNumber) && Objects.equals(email, customer.email) && Objects.equals(password, customer.password) && Objects.equals(createdOn, customer.createdOn) && Objects.equals(creditCard, customer.creditCard) && Objects.equals(address, customer.address) && Objects.equals(orders, customer.orders) && Objects.equals(customerCart, customer.customerCart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, firstName, lastName, contactNumber, email, password, createdOn, creditCard, address, orders, customerCart);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdOn=" + createdOn +
                ", creditCard=" + creditCard +
                ", address=" + address +
                ", orders=" + orders +
                ", customerCart=" + customerCart +
                '}';
    }
}
