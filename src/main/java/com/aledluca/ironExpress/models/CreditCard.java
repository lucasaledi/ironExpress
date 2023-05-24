package com.aledluca.ironExpress.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;

public class CreditCard {
    @NotNull(message = "Credit Card number cannot be null")
    @Pattern(regexp = "[0-9]{16,18}", message = "Invalid card number")
    private String cardNumber;
    @Pattern(regexp = "[0-9]{2}/[0-9]{2,4}", message = "Invalid expire date. Format must be MM/YY or MM/YYYY")
    private String expireDate;
    @Pattern(regexp = "[0-9]{3}", message = "Invalid CVV. Must be numeric 3 digits length.")
    private String cvv;

    public CreditCard() {
    }

    public CreditCard(String cardNumber, String expireDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expireDate = expireDate;
        this.cvv = cvv;
    }


}
