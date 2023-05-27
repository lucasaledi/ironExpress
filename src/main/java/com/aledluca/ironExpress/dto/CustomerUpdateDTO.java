package com.aledluca.ironExpress.dto;

import com.aledluca.ironExpress.models.Address;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.*;

import java.util.HashMap;
import java.util.Map;

public class CustomerUpdateDTO {
    @Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in first name")
    private String firstName;
    @Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in last name")
    private String lastName;
    @Pattern(regexp = "[6789]{1}[0-9]{9}", message = "Enter valid 10 digit contact number")
    private String contactNumber;
    @Email
    private String email;
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can include A-Z, a-z, 0-9, or special characters !@#$%^&*_")
    private String password;
    private Map<String, Address> address = new HashMap<>();

    public CustomerUpdateDTO() {
    }

    public CustomerUpdateDTO(String firstName, String lastName, String contactNumber, String email, String password, Map<String, Address> address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.address = address;
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

    public Map<String, Address> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Address> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerUpdateDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                '}';
    }
}
