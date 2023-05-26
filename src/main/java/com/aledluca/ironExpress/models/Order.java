package com.aledluca.ironExpress.models;

import com.aledluca.ironExpress.enums.OrderStatusValues;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Order() {
    }

    public Order(LocalDate date, OrderStatusValues orderStatus, Double total, String cardNumber, Customer customer, List<CartItem> ordercartItems, Address address) {
        this.date = date;
        this.orderStatus = orderStatus;
        this.total = total;
        this.cardNumber = cardNumber;
        this.customer = customer;
        this.ordercartItems = ordercartItems;
        this.address = address;
    }

    public Order(Integer orderId, LocalDate date, OrderStatusValues orderStatus, Double total, String cardNumber, Customer customer, List<CartItem> ordercartItems, Address address) {
        this.orderId = orderId;
        this.date = date;
        this.orderStatus = orderStatus;
        this.total = total;
        this.cardNumber = cardNumber;
        this.customer = customer;
        this.ordercartItems = ordercartItems;
        this.address = address;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public OrderStatusValues getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusValues orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CartItem> getOrderCartItems() {
        return ordercartItems;
    }

    public void setOrderCartItems(List<CartItem> ordercartItems) {
        this.ordercartItems = ordercartItems;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && Objects.equals(date, order.date) && orderStatus == order.orderStatus && Objects.equals(total, order.total) && Objects.equals(cardNumber, order.cardNumber) && Objects.equals(customer, order.customer) && Objects.equals(ordercartItems, order.ordercartItems) && Objects.equals(address, order.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, date, orderStatus, total, cardNumber, customer, ordercartItems, address);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", date=" + date +
                ", orderStatus=" + orderStatus +
                ", total=" + total +
                ", cardNumber='" + cardNumber + '\'' +
                ", customer=" + customer +
                ", ordercartItems=" + ordercartItems +
                ", address=" + address +
                '}';
    }
}
