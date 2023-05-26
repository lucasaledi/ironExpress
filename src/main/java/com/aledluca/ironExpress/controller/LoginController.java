package com.aledluca.ironExpress.controller;

import com.aledluca.ironExpress.dto.CustomerDTO;
import com.aledluca.ironExpress.dto.SellerDTO;
import com.aledluca.ironExpress.dto.SessionDTO;
import com.aledluca.ironExpress.models.Customer;
import com.aledluca.ironExpress.models.Seller;
import com.aledluca.ironExpress.models.UserSession;
import com.aledluca.ironExpress.service.CustomerService;
import com.aledluca.ironExpress.service.LoginLogoutService;
import com.aledluca.ironExpress.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private LoginLogoutService loginService;
    @Autowired
    private SellerService sellerService;


    // Handler to register a new customer
    @PostMapping(value = "/register/customer", consumes = "application/json")
    public ResponseEntity<Customer> registerAccountHandler(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.CREATED);
    }

    // Handler to login a customer
    @PostMapping(value = "/login/customer", consumes = "application/json")
    public ResponseEntity<UserSession> loginCustomerHandler(@Valid @RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<>(loginService.loginCustomer(customerDTO), HttpStatus.ACCEPTED);
    }

    // Handler to logout a customer
    @PostMapping(value = "/logout/customer", consumes = "application/json")
    public ResponseEntity<SessionDTO> logoutCustomerHandler(@RequestBody SessionDTO sessionToken){
        return new ResponseEntity<>(loginService.logoutCustomer(sessionToken), HttpStatus.ACCEPTED);
    }


    // SELLER REGISTER LOGIN LOGOUT HANDLER
    @PostMapping(value = "/register/seller", consumes = "application/json")
    public ResponseEntity<Seller> registerSellerAccountHandler(@Valid @RequestBody Seller seller) {
        return new ResponseEntity<>(sellerService.addSeller(seller), HttpStatus.CREATED);
    }

    // Handler to login seller
    @PostMapping(value = "/login/seller", consumes = "application/json")
    public ResponseEntity<UserSession> loginSellerHandler(@Valid @RequestBody SellerDTO seller){
        return new ResponseEntity<>(loginService.loginSeller(seller), HttpStatus.ACCEPTED);
    }


    // Handler to logout a user
    @PostMapping(value = "/logout/seller", consumes = "application/json")
    public ResponseEntity<SessionDTO> logoutSellerHandler(@RequestBody SessionDTO sessionToken){
        return new ResponseEntity<>(loginService.logoutSeller(sessionToken), HttpStatus.ACCEPTED);
    }

}
