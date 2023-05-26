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

    // Get a list of all customers
//    @GetMapping("/customers")
//    public ResponseEntity<List<Customer>> getAllCustomersHandler(@RequestHeader("token") String token){
//        return new ResponseEntity<>(customerService.getAllCustomers(token), HttpStatus.OK);
//    }
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomersHandler(){
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    // Handler to Get a customer details of currently logged in user - sends data as per token
//    @GetMapping("/customer/current")
//    public ResponseEntity<Customer> getLoggedInCustomerDetailsHandler(@RequestHeader("token") String token){
//        return new ResponseEntity<>(customerService.getLoggedInCustomerDetails(token), HttpStatus.OK);
//    }
    @GetMapping("/customer/current/{userId}")
    public ResponseEntity<Customer> getLoggedInCustomerDetailsHandler(@PathVariable Integer userId){
        return new ResponseEntity<>(customerService.getLoggedInCustomerDetails(userId), HttpStatus.OK);
    }

    // Handler to update a customer
//    @PutMapping("/customer")
//    public ResponseEntity<Customer> updateCustomerHandler(@Valid @RequestBody CustomerUpdateDTO customerUpdate, @RequestHeader("token") String token){
//        return new ResponseEntity<>(customerService.updateCustomer(customerUpdate, token), HttpStatus.ACCEPTED);
//    }
    @PutMapping("/customer/{userId}")
    public ResponseEntity<Customer> updateCustomerHandler(@Valid @RequestBody CustomerUpdateDTO customerUpdate, @PathVariable Integer userId){
        return new ResponseEntity<>(customerService.updateCustomer(customerUpdate, userId), HttpStatus.ACCEPTED);
    }

    // Handler to update a customer email or contact number
//    @PutMapping("/customer/update/credentials")
//    public ResponseEntity<Customer> updateCustomerMobileEmailHandler(@Valid @RequestBody CustomerUpdateDTO customerUpdate, @RequestHeader("token") String token){
//        return new ResponseEntity<>(customerService.updateCustomerContactNumberOrEmail(customerUpdate, token), HttpStatus.ACCEPTED);
//    }
    @PutMapping("/customer/update/credentials/{userId}")
    public ResponseEntity<Customer> updateCustomerMobileEmailHandler(@Valid @RequestBody CustomerUpdateDTO customerUpdate, @PathVariable Integer userId){
        return new ResponseEntity<>(customerService.updateCustomerContactNumberOrEmail(customerUpdate, userId), HttpStatus.ACCEPTED);
    }

    // Handler to update customer password
//    @PutMapping("/customer/update/password")
//    public ResponseEntity<SessionDTO> updateCustomerPasswordHandler(@Valid @RequestBody CustomerDTO customerDto, @RequestHeader("token") String token){
//        return new ResponseEntity<>(customerService.updateCustomerPassword(customerDto, token), HttpStatus.ACCEPTED);
//    }
    @PutMapping("/customer/update/password/{userId}")
    public ResponseEntity<SessionDTO> updateCustomerPasswordHandler(@Valid @RequestBody CustomerDTO customerDto, @PathVariable Integer userId){
        return new ResponseEntity<>(customerService.updateCustomerPassword(customerDto, userId), HttpStatus.ACCEPTED);
    }

    // Handler to add or update new customer address
//    @PutMapping("/customer/update/address")
//    public ResponseEntity<Customer> updateAddressHandler(@Valid @RequestBody Address address, @RequestParam("type") String type, @RequestHeader("token") String token){
//        return new ResponseEntity<>(customerService.updateAddress(address, type, token), HttpStatus.ACCEPTED);
//    }

    @PutMapping("/customer/update/address/{userId}")
    public ResponseEntity<Customer> updateAddressHandler(@Valid @RequestBody Address address, @RequestParam("type") String type, @PathVariable Integer userId){
        return new ResponseEntity<>(customerService.updateAddress(address, type, userId), HttpStatus.ACCEPTED);
    }

    // Handler to update Credit card details
//    @PutMapping("/customer/update/card")
//    public ResponseEntity<Customer> updateCreditCardHandler(@RequestHeader("token") String token, @Valid @RequestBody CreditCard newCard){
//        return new ResponseEntity<>(customerService.updateCreditCardDetails(token, newCard), HttpStatus.ACCEPTED);
//    }
    @PutMapping("/customer/update/card/{userId}")
    public ResponseEntity<Customer> updateCreditCardHandler(@Valid @RequestBody CreditCard newCard, @PathVariable Integer userId){
        return new ResponseEntity<>(customerService.updateCreditCardDetails(newCard, userId), HttpStatus.ACCEPTED);
    }

    // Handler to delete a user address
//    @DeleteMapping("/customer/delete/address")
//    public ResponseEntity<Customer> deleteAddressHandler(@RequestParam("type") String type, @RequestHeader("token") String token){
//        return new ResponseEntity<>(customerService.deleteAddress(type, token), HttpStatus.ACCEPTED);
//    }
    @DeleteMapping("/customer/delete/address/{userId}")
    public ResponseEntity<Customer> deleteAddressHandler(@RequestParam("type") String type, @PathVariable Integer userId){
        return new ResponseEntity<>(customerService.deleteAddress(type, userId), HttpStatus.ACCEPTED);
    }

    // Handler to delete customer
//    @DeleteMapping("/customer")
//    public ResponseEntity<SessionDTO> deleteCustomerHandler(@Valid @RequestBody CustomerDTO customerDto, @RequestHeader("token") String token){
//        return new ResponseEntity<>(customerService.deleteCustomer(customerDto, token), HttpStatus.ACCEPTED);
//    }
    @DeleteMapping("/customer/{userId}")
    public ResponseEntity<SessionDTO> deleteCustomerHandler(@Valid @RequestBody CustomerDTO customerDto, @PathVariable Integer userId){
        return new ResponseEntity<>(customerService.deleteCustomer(customerDto, userId), HttpStatus.ACCEPTED);
    }

    // Handler to get a customer's order
//    @GetMapping("/customer/orders")
//    public ResponseEntity<List<Order>> getCustomerOrdersHandler(@RequestHeader("token") String token){
//        return new ResponseEntity<>(customerService.getCustomerOrders(token), HttpStatus.ACCEPTED);
//    }
    @GetMapping("/customer/orders/{userId}")
    public ResponseEntity<List<Order>> getCustomerOrdersHandler(@PathVariable Integer userId){
        return new ResponseEntity<>(customerService.getCustomerOrders(userId), HttpStatus.ACCEPTED);
    }

}
