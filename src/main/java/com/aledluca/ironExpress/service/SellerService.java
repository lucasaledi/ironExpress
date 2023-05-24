package com.aledluca.ironExpress.service;

import com.aledluca.ironExpress.dto.SellerDTO;
import com.aledluca.ironExpress.dto.SellerUpdateDTO;
import com.aledluca.ironExpress.dto.SessionDTO;
import com.aledluca.ironExpress.exception.*;
import com.aledluca.ironExpress.models.*;
import com.aledluca.ironExpress.repository.SellerRepository;
import com.aledluca.ironExpress.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private LoginLogoutService loginService;
    @Autowired
    private SessionRepository sessionRepository;

    // Add new seller to repo
    public Seller addSeller(Seller seller) throws SellerException{
        seller.setCreatedOn(LocalDateTime.now());
        seller.setProduct(new ArrayList<Product>());
        Optional<Seller> existingSeller = sellerRepository.findByContactNumber(seller.getContactNumber());
        if (existingSeller.isPresent()) {
            throw new SellerException("Seller already exists");
        } else {
            sellerRepository.save(seller);
        }
        return seller;
    }

    // Get logged-in seller details
    public Seller getCurrentlyLoggedInSeller(String token) throws SellerNotFoundException {
        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Seller> opt = sellerRepository.findById(user.getUserId());
        if(opt.isEmpty()) {
            throw new SellerNotFoundException("Customer does not exist");
        }
        Seller existingSeller = opt.get();
        return existingSeller;
    }

    // Get all sellers in repo - only seller or admin can get all customers - check validity of seller token
    public List<Seller> getAllSellers(String token) throws SellerNotFoundException {
        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token.");
        }
        loginService.checkTokenStatus(token);
        List<Seller> sellers = sellerRepository.findAll();
        if(sellers.size() == 0) {
            throw new SellerNotFoundException("No record exists");
        } else {
            return sellers;
        }
    }

    // Get seller by Id
    public Seller getSellerById(Integer sellerId) {
        Optional<Seller> seller = sellerRepository.findById(sellerId);
        if(seller.isPresent()) {
            return seller.get();
        } else {
            throw new SellerException("Seller not found for id: " + sellerId);
        }
    }
    
    // Update seller data - either by contact number or email
    public Seller updateSeller(SellerUpdateDTO seller, String token) throws SellerNotFoundException {
        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }
        loginService.checkTokenStatus(token);
        Optional<Seller> opt = sellerRepository.findByContactNumber(seller.getContactNumber());
        Optional<Seller> res = sellerRepository.findByEmail(seller.getEmail());
        if(opt.isEmpty() && res.isEmpty()) {
            throw new SellerNotFoundException("Seller does not exist with given contact number or email");
        }
        Seller existingSeller = null;
        if(opt.isPresent()) {
            existingSeller = opt.get();
        } else {
            existingSeller = res.get();
        }
        UserSession user = sessionRepository.findByToken(token).get();
        if(existingSeller.getSellerId() == user.getUserId()) {
            if(seller.getFirstName() != null) {
                existingSeller.setFirstName(seller.getFirstName());
            }
            if(seller.getLastName() != null) {
                existingSeller.setLastName(seller.getLastName());
            }
            if(seller.getEmail() != null) {
                existingSeller.setEmail(seller.getEmail());
            }
            if(seller.getContactNumber() != null) {
                existingSeller.setContactNumber(seller.getContactNumber());
            }
            if(seller.getPassword() != null) {
                existingSeller.setPassword(seller.getPassword());
            }
            sellerRepository.save(existingSeller);
            return existingSeller;
        }
        else {
            throw new CustomerException("Error in updating Seller data. Verification failed.");
        }
    }

    // Delete logged-in seller by contact number
    public SessionDTO deleteSeller(SellerDTO sellerDTO, String token) throws SellerNotFoundException {
        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Seller> opt = sellerRepository.findById(user.getUserId());
        if(opt.isEmpty()) {
            throw new SellerNotFoundException("Seller does not exist");
        }
        Seller existingSeller = opt.get();
        SessionDTO session = new SessionDTO();
        session.setMessage("");
        session.setToken(token);
        if (existingSeller.getContactNumber().equals(sellerDTO.getContactNumber())
                && existingSeller.getPassword().equals(sellerDTO.getPassword())) {
            sellerRepository.delete(existingSeller);
            loginService.logoutSeller(session);
            session.setMessage("Deleted account and logged out successfully");
            return session;
        } else {
            throw new SellerException("Verification error in deleting account. Please, re-check details");
        }
    }

    public Seller updateSellerContactNumberOrEmail(SellerUpdateDTO sellerUpdateDTO, String token) throws SellerNotFoundException {
        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Seller> opt = sellerRepository.findById(user.getUserId());
        if(opt.isEmpty()){
            throw new SellerNotFoundException("Seller doe not exist");
        }
        Seller existingSeller = opt.get();
        if(sellerUpdateDTO.getEmail() != null) {
            existingSeller.setEmail(sellerUpdateDTO.getEmail());
        }
        if(sellerUpdateDTO.getContactNumber() != null) {
            existingSeller.setContactNumber(sellerUpdateDTO.getContactNumber());
        }
        sellerRepository.save(existingSeller);
        return existingSeller;
    }

    // Get seller by contact number
    public Seller getSellerByContactNumber(String contactNumber, String token) throws SellerException {
        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }
        loginService.checkTokenStatus(token);
        Optional<Seller> opt = sellerRepository.findByContactNumber(contactNumber);
        if(opt.isEmpty()) {
            throw new SellerNotFoundException("Seller does not exist");
        }
        Seller existingSeller = opt.get();
        return existingSeller;
    }

    // Method to update password - based on current token
    public SessionDTO updateSellerPassword(SellerDTO sellerDTO, String token) {
        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Seller> opt = sellerRepository.findById(user.getUserId());
        if(opt.isEmpty()) {
            throw new SellerException("Seller does not exist");
        }
        Seller existingSeller = opt.get();
        if(sellerDTO.getContactNumber().equals(existingSeller.getContactNumber()) == false) {
            throw new SellerException("Verification error. Mobile number does not match");
        }
        existingSeller.setPassword(sellerDTO.getPassword());
        sellerRepository.save(existingSeller);
        SessionDTO session = new SessionDTO();
        session.setToken(token);
        loginService.logoutSeller(session);
        session.setMessage("Updated password and logged out. Login again with new password");
        return session;
    }

}
