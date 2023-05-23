package com.aledluca.ironExpress.service;

import com.aledluca.ironExpress.exception.CustomerException;
import com.aledluca.ironExpress.exception.CustomerNotFoundException;
import com.aledluca.ironExpress.exception.LoginException;
import com.aledluca.ironExpress.models.Cart;
import com.aledluca.ironExpress.models.Customer;
import com.aledluca.ironExpress.models.Order;
import com.aledluca.ironExpress.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        customer.setCreatedOn(LocalDateTime.now());
        Cart cart = new Cart();
        customer.setCustomerCart(cart);
        customer.setOrders(new ArrayList<Order>());
        Optional<Customer> existingCustomer = customerRepository.findByContactNumber(customer.getContactNumber());
        if (existingCustomer.isPresent()) {
            throw new CustomerException("Customer already exists");
        } else {
            customerRepository.save(customer);
        }
        return customer;
    }

    public Customer getLoggedInCustomerDetails(String token){

        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Optional<Customer> opt = customerRepository.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        return existingCustomer;
    }


}
