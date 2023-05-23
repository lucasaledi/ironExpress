package com.aledluca.ironExpress.dto;

import jakarta.validation.constraints.*;

public class SellerDTO {
    @NotNull(message="Contact number cannot be null")
    @Pattern(regexp="[6789]{1}[0-9]{9}", message="Enter a valid contact number")
    private String contactNumber;
    @Pattern(regexp="[A-Za-z0-9!@#$%^&*_]{8,15}", message="Please Enter a valid Password")
    private String password;

    public SellerDTO() {
    }

    public SellerDTO(String contactNumber, String password) {
        this.contactNumber = contactNumber;
        this.password = password;
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
}
