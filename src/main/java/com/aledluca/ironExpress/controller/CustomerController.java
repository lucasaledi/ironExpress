package com.aledluca.ironExpress.controller;

import com.aledluca.ironExpress.dto.CustomerDTO;
import com.aledluca.ironExpress.dto.CustomerUpdateDTO;
import com.aledluca.ironExpress.dto.SessionDTO;
import com.aledluca.ironExpress.models.Address;
import com.aledluca.ironExpress.models.CreditCard;
import com.aledluca.ironExpress.models.Customer;
import com.aledluca.ironExpress.models.Order;
import com.aledluca.ironExpress.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // Handler to get a list of all customers
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomersHandler(@RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.getAllCustomers(token), HttpStatus.ACCEPTED);
    }

    // Handler to Get a customer details of currently logged in user - sends data as per token
    @GetMapping("/customer/current")
    public ResponseEntity<Customer> getLoggedInCustomerDetailsHandler(@RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.getLoggedInCustomerDetails(token), HttpStatus.ACCEPTED);
    }

    // Handler to Update a customer
    @PutMapping("/customer")
    public ResponseEntity<Customer> updateCustomerHandler(@Valid @RequestBody CustomerUpdateDTO customerUpdate, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.updateCustomer(customerUpdate, token), HttpStatus.ACCEPTED);
    }

    // Handler to update a customer email-id or mobile no
    @PutMapping("/customer/update/credentials")
    public ResponseEntity<Customer> updateCustomerMobileEmailHandler(@Valid @RequestBody CustomerUpdateDTO customerUpdate, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.updateCustomerContactNumberOrEmail(customerUpdate, token), HttpStatus.ACCEPTED);
    }

    // Handler to update customer password
    @PutMapping("/customer/update/password")
    public ResponseEntity<SessionDTO> updateCustomerPasswordHandler(@Valid @RequestBody CustomerDTO customerDto, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.updateCustomerPassword(customerDto, token), HttpStatus.ACCEPTED);
    }

    // Handler to Add or update new customer Address
    @PutMapping("/customer/update/address")
    public ResponseEntity<Customer> updateAddressHandler(@Valid @RequestBody Address address, @RequestParam("type") String type, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.updateAddress(address, type, token), HttpStatus.ACCEPTED);
    }

    // Handler to update Credit card details
    @PutMapping("/customer/update/card")
    public ResponseEntity<Customer> updateCreditCardHandler(@RequestHeader("token") String token, @Valid @RequestBody CreditCard newCard){
        return new ResponseEntity<>(customerService.updateCreditCardDetails(token, newCard), HttpStatus.ACCEPTED);
    }

    // Handler to delete a user address
    @DeleteMapping("/customer/delete/address")
    public ResponseEntity<Customer> deleteAddressHandler(@RequestParam("type") String type, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.deleteAddress(type, token), HttpStatus.ACCEPTED);
    }

    // Handler to delete customer
    @DeleteMapping("/customer")
    public ResponseEntity<SessionDTO> deleteCustomerHandler(@Valid @RequestBody CustomerDTO customerDto, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.deleteCustomer(customerDto, token), HttpStatus.ACCEPTED);
    }

    // Handler to get a customer's order
    @GetMapping("/customer/orders")
    public ResponseEntity<List<Order>> getCustomerOrdersHandler(@RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.getCustomerOrders(token), HttpStatus.ACCEPTED);
    }

}
