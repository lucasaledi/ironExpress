package com.aledluca.ironExpress.dto;

import jakarta.validation.constraints.*;

public class CustomerDTO {
    @NotNull(message = "Contact number cannot be null")
    @Pattern(regexp = "[6789][0-9]{9}", message = "Enter valid contact number")
    private String contactNumber;
    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can include A-Z, a-z, 0-9, or special characters !@#$%^&*_")
    private String password;
    @Email
    private String email;


    public CustomerDTO() {
    }

    public CustomerDTO(String contactNumber, String password, String email) {
        this.contactNumber = contactNumber;
        this.password = password;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
