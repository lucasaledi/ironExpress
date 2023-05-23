package com.aledluca.ironExpress.service;

import com.aledluca.ironExpress.dto.CustomerDTO;
import com.aledluca.ironExpress.dto.CustomerUpdateDTO;
import com.aledluca.ironExpress.dto.SessionDTO;
import com.aledluca.ironExpress.exception.CustomerException;
import com.aledluca.ironExpress.exception.CustomerNotFoundException;
import com.aledluca.ironExpress.exception.LoginException;
import com.aledluca.ironExpress.models.*;
import com.aledluca.ironExpress.repository.CustomerRepository;
import com.aledluca.ironExpress.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoginLogoutService loginService;
    @Autowired
    private SessionRepository sessionRepository;

    // Add new customer to repo
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
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Customer> opt = customerRepository.findById(user.getUserId());
        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");
        Customer existingCustomer = opt.get();
        return existingCustomer;
    }

    // Method to get all customers - only seller or admin can get all customers - check validity of seller token
    public List<Customer> getAllCustomers(String token) throws CustomerNotFoundException {
        // update to seller
        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token.");
        }
        loginService.checkTokenStatus(token);
        List<Customer> customers = customerRepository.findAll();
        if(customers.size() == 0) {
            throw new CustomerNotFoundException("No record exists");
        } else {
            return customers;
        }
    }

    // Method to update entire customer details - either by contact number or email
    public Customer updateCustomer(CustomerUpdateDTO customer, String token) throws CustomerNotFoundException {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }
        loginService.checkTokenStatus(token);
        Optional<Customer> opt = customerRepository.findByContactNumber(customer.getContactNumber());
        Optional<Customer> res = customerRepository.findByEmail(customer.getEmail());
        if(opt.isEmpty() && res.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist with given mobile no or email-id");
        }
        Customer existingCustomer = null;
        if(opt.isPresent()) {
            existingCustomer = opt.get();
        }
        else {
            existingCustomer = res.get();
        }
        UserSession user = sessionRepository.findByToken(token).get();
        if(existingCustomer.getCustomerId() == user.getUserId()) {
            if(customer.getFirstName() != null) {
                existingCustomer.setFirstName(customer.getFirstName());
            }
            if(customer.getLastName() != null) {
                existingCustomer.setLastName(customer.getLastName());
            }
            if(customer.getEmail() != null) {
                existingCustomer.setEmail(customer.getEmail());
            }
            if(customer.getContactNumber() != null) {
                existingCustomer.setContactNumber(customer.getContactNumber());
            }
            if(customer.getPassword() != null) {
                existingCustomer.setPassword(customer.getPassword());
            }
            if(customer.getAddress() != null) {
                for(Map.Entry<String, Address> values : customer.getAddress().entrySet()) {
                    existingCustomer.getAddress().put(values.getKey(), values.getValue());
                }
            }
            customerRepository.save(existingCustomer);
            return existingCustomer;
        }
        else {
            throw new CustomerException("Error in updating Customer data. Verification failed.");
        }
    }

    // Method to update customer mobile number - details updated for current logged in user
    public Customer updateCustomerContactNumberOrEmail(CustomerUpdateDTO customerUpdateDTO, String token) throws CustomerNotFoundException {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Customer> opt = customerRepository.findById(user.getUserId());
        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");
        Customer existingCustomer = opt.get();
        if(customerUpdateDTO.getEmail() != null) {
            existingCustomer.setEmail(customerUpdateDTO.getEmail());
        }
        existingCustomer.setContactNumber(customerUpdateDTO.getContactNumber());
        customerRepository.save(existingCustomer);
        return existingCustomer;

    }

    // Method to update password - based on current token
    public SessionDTO updateCustomerPassword(CustomerDTO customerDTO, String token) {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Customer> opt = customerRepository.findById(user.getUserId());
        if(opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }
        Customer existingCustomer = opt.get();
        if(customerDTO.getContactNumber().equals(existingCustomer.getContactNumber()) == false) {
            throw new CustomerException("Verification error. Contact number does not match");
        }
        existingCustomer.setPassword(customerDTO.getPassword());
        customerRepository.save(existingCustomer);
        SessionDTO session = new SessionDTO();
        session.setToken(token);
        loginService.logoutCustomer(session);
        session.setMessage("Updated password and logged out. Login again with new password");
        return session;
    }

    // Method to add/update Address
    public Customer updateAddress(Address address, String type, String token) throws CustomerException {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Customer> opt = customerRepository.findById(user.getUserId());
        if(opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }
        Customer existingCustomer = opt.get();
        existingCustomer.getAddress().put(type, address);
        return customerRepository.save(existingCustomer);
    }

    // Method to update Credit card
    public Customer updateCreditCardDetails(String token, CreditCard card) throws CustomerException{

        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Customer> opt = customerRepository.findById(user.getUserId());
        if(opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }
        Customer existingCustomer = opt.get();
        existingCustomer.setCreditCard(card);
        return customerRepository.save(existingCustomer);
    }

    // Handler to delete a customer's address 
    public Customer deleteAddress(String type, String token) throws CustomerException, CustomerNotFoundException {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Customer> opt = customerRepository.findById(user.getUserId());
        if(opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }
        Customer existingCustomer = opt.get();
        if(existingCustomer.getAddress().containsKey(type) == false) {
            throw new CustomerException("Address type does not exist");
        }
        existingCustomer.getAddress().remove(type);
        return customerRepository.save(existingCustomer);
    }

    // Method to delete a customer by mobile id
    public SessionDTO deleteCustomer(CustomerDTO customerDTO, String token) throws CustomerNotFoundException {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Customer> opt = customerRepository.findById(user.getUserId());
        if(opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }
        Customer existingCustomer = opt.get();
        SessionDTO session = new SessionDTO();
        session.setMessage("");
        session.setToken(token);
        if(existingCustomer.getContactNumber().equals(customerDTO.getContactNumber())
                && existingCustomer.getPassword().equals(customerDTO.getPassword())) {
            customerRepository.delete(existingCustomer);
            loginService.logoutCustomer(session);
            session.setMessage("Deleted account and logged out successfully");
            return session;
        }
        else {
            throw new CustomerException("Verification error in deleting account. Please re-check details");
        }
    }

    // Handler to get a customer's orders
    public List<Order> getCustomerOrders(String token) throws CustomerException {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Customer> opt = customerRepository.findById(user.getUserId());
        if(opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }
        Customer existingCustomer = opt.get();
        List<Order> myOrders = existingCustomer.getOrders();
        if(myOrders.size() == 0) {
            throw new CustomerException("No orders found");
        }
        return myOrders;
    }

}
