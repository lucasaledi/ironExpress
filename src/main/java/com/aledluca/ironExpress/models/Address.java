package com.aledluca.ironExpress.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.*;

import java.util.Objects;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer addressId;
    @NotNull(message = "Street name cannot be null")
    @Pattern(regexp = "[A-Za-z0-9\\s-]{3,}", message = "Not a valid street name")
    private String streetName;
    @NotNull(message = "Build number name cannot be null")
    @Pattern(regexp = "[A-Za-z0-9\\s-]{3,}", message = "Not a valid street number")
    private String buildNumber;
    @Pattern(regexp = "[A-Za-z0-9\\s-]{3,}", message = "Not a valid address complement")
    private String addressComplement;
    @NotNull(message = "Neighborhood name cannot be null")
    @Pattern(regexp = "[A-Za-z0-9\\s-]{3,}", message = "Not a valid neighborhood name")
    private String neighborhood;
    @NotNull(message = "City name cannot be null")
    @Pattern(regexp = "[A-Za-z\\s]{2,}", message = "Not a valid city name")
    private String city;
    @NotNull(message = "Country name cannot be null")
    private String country;
    @NotNull(message = "Zip code cannot be null")
    @Pattern(regexp = "[0-9]{6}", message = "Zip code not valid. Must be 6 digits")
    private String zipCode;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Customer customer;


    public Address() {
    }

    public Address(String streetName, String buildNumber, String addressComplement, String neighborhood, String city, String country, String zipCode, Customer customer) {
        this.streetName = streetName;
        this.buildNumber = buildNumber;
        this.addressComplement = addressComplement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.customer = customer;
    }

    public Address(Integer addressId, String streetName, String buildNumber, String addressComplement, String neighborhood, String city, String country, String zipCode, Customer customer) {
        this.addressId = addressId;
        this.streetName = streetName;
        this.buildNumber = buildNumber;
        this.addressComplement = addressComplement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.customer = customer;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public void setAddressComplement(String addressComplement) {
        this.addressComplement = addressComplement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(addressId, address.addressId) && Objects.equals(streetName, address.streetName) && Objects.equals(buildNumber, address.buildNumber) && Objects.equals(addressComplement, address.addressComplement) && Objects.equals(neighborhood, address.neighborhood) && Objects.equals(city, address.city) && Objects.equals(country, address.country) && Objects.equals(zipCode, address.zipCode) && Objects.equals(customer, address.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, streetName, buildNumber, addressComplement, neighborhood, city, country, zipCode, customer);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", streetName='" + streetName + '\'' +
                ", buildNumber='" + buildNumber + '\'' +
                ", addressComplement='" + addressComplement + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", customer=" + customer +
                '}';
    }
}
