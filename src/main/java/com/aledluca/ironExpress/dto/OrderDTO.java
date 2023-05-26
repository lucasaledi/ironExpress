package com.aledluca.ironExpress.dto;

import com.aledluca.ironExpress.models.CreditCard;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;

public class OrderDTO {
    @NotNull
    @Embedded
    private CreditCard cardNumber;
    @NotNull
    private String addressType;


    public OrderDTO() {
    }

    public OrderDTO(CreditCard cardNumber, String addressType) {
        this.cardNumber = cardNumber;
        this.addressType = addressType;
    }

    public CreditCard getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(CreditCard cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "cardNumber=" + cardNumber +
                ", addressType='" + addressType + '\'' +
                '}';
    }
}
