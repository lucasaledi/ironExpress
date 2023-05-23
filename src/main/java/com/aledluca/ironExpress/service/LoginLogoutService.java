package com.aledluca.ironExpress.service;

import com.aledluca.ironExpress.dto.CustomerDTO;
import com.aledluca.ironExpress.dto.SellerDTO;
import com.aledluca.ironExpress.dto.SessionDTO;
import com.aledluca.ironExpress.exception.CustomerNotFoundException;
import com.aledluca.ironExpress.exception.LoginException;
import com.aledluca.ironExpress.exception.SellerNotFoundException;
import com.aledluca.ironExpress.models.Customer;
import com.aledluca.ironExpress.models.Seller;
import com.aledluca.ironExpress.models.UserSession;
import com.aledluca.ironExpress.repository.CustomerRepository;
import com.aledluca.ironExpress.repository.SellerRepository;
import com.aledluca.ironExpress.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class LoginLogoutService {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SellerRepository sellerRepository;


    // Method to log in a customer
    public UserSession loginCustomer(CustomerDTO loginCustomer) {
        Optional<Customer> res = customerRepository.findByContactNumber(loginCustomer.getContactNumber());
        if(res.isEmpty()) {
            throw new CustomerNotFoundException("Customer record does not exist with given mobile number");
        }
        Customer existingCustomer = res.get();
        Optional<UserSession> opt = sessionRepository.findByUserId(existingCustomer.getCustomerId());
        if(opt.isPresent()) {
            UserSession user = opt.get();
            if(user.getSessionEndTime().isBefore(LocalDateTime.now())) {
                sessionRepository.delete(user);
            }
            else {
                throw new LoginException("User already logged in");
            }
        }
        if(existingCustomer.getPassword().equals(loginCustomer.getPassword())) {
            UserSession newSession = new UserSession();
            newSession.setUserId(existingCustomer.getCustomerId());
            newSession.setUserType("customer");
            newSession.setSessionStartTime(LocalDateTime.now());
            newSession.setSessionEndTime(LocalDateTime.now().plusHours(1));
            UUID uuid = UUID.randomUUID();
            String token = "customer_" + uuid.toString().split("-")[0];
            newSession.setToken(token);
            return sessionRepository.save(newSession);
        }
        else {
            throw new LoginException("Password Incorrect. Try again.");
        }
    }

    // Method to log out a customer
    public SessionDTO logoutCustomer(SessionDTO sessionToken) {
        String token = sessionToken.getToken();
        checkTokenStatus(token);
        Optional<UserSession> opt = sessionRepository.findByToken(token);
        if(opt.isEmpty()) {
            throw new LoginException("User not logged in. Invalid session token. Login again.");
        }
        UserSession session = opt.get();
        sessionRepository.delete(session);
        sessionToken.setMessage("Logged out successfully.");
        return sessionToken;
    }

    // Method to check status of session token
    public void checkTokenStatus(String token) {
        Optional<UserSession> opt = sessionRepository.findByToken(token);
        if(opt.isPresent()) {
            UserSession session = opt.get();
            LocalDateTime endTime = session.getSessionEndTime();
            boolean flag = false;
            if(endTime.isBefore(LocalDateTime.now())) {
                sessionRepository.delete(session);
                flag = true;
            }
            deleteExpiredTokens();
            if(flag) {
                throw new LoginException("Session expired. Login Again");
            }
        }
        else {
            throw new LoginException("User not logged in. Invalid session token. Please login first.");
        }
    }

    // Method to login a valid seller and generate a seller token
    public UserSession loginSeller(SellerDTO seller) {
        Optional<Seller> res = sellerRepository.findByContactNumber(seller.getContactNumber());
        if(res.isEmpty()) {
            throw new SellerNotFoundException("No seller was found with given contact number");
        }
        Seller existingSeller = res.get();
        Optional<UserSession> opt = sessionRepository.findByUserId(existingSeller.getSellerId());
        if(opt.isPresent()) {
            UserSession user = opt.get();
            if(user.getSessionEndTime().isBefore(LocalDateTime.now())) {
                sessionRepository.delete(user);
            }
            else {
                throw new LoginException("User already logged in");
            }
        }
        if(existingSeller.getPassword().equals(seller.getPassword())) {
            UserSession newSession = new UserSession();
            newSession.setUserId(existingSeller.getSellerId());
            newSession.setUserType("seller");
            newSession.setSessionStartTime(LocalDateTime.now());
            newSession.setSessionEndTime(LocalDateTime.now().plusHours(1));
            UUID uuid = UUID.randomUUID();
            String token = "seller_" + uuid.toString().split("-")[0];
            newSession.setToken(token);
            return sessionRepository.save(newSession);
        }
        else {
            throw new LoginException("Password Incorrect. Try again.");
        }
    }

    // Method to log out a seller and delete session toke
    public SessionDTO logoutSeller(SessionDTO session) {
        String token = session.getToken();
        checkTokenStatus(token);
        Optional<UserSession> opt = sessionRepository.findByToken(token);
        if(opt.isEmpty()) {
            throw new LoginException("User not logged in. Invalid session token. Login Again.");
        }
        UserSession user = opt.get();
        sessionRepository.delete(user);
        session.setMessage("Logged out successfully.");
        return session;
    }

    // Method to delete expired tokens
    public void deleteExpiredTokens() {

        //System.out.println("Inside delete tokens");

        List<UserSession> users = sessionRepository.findAll();

        //System.out.println(users);

        if(users.size() > 0) {
            for(UserSession user:users) {
                //System.out.println(user.getUserId());
                LocalDateTime endTime = user.getSessionEndTime();
                if(endTime.isBefore(LocalDateTime.now())) {
                    //System.out.println(user.getUserId());
                    sessionRepository.delete(user);
                }
            }
        }
    }

}
