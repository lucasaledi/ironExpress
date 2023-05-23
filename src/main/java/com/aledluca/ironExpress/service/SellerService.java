package com.aledluca.ironExpress.service;

import com.aledluca.ironExpress.exception.CustomerException;
import com.aledluca.ironExpress.exception.LoginException;
import com.aledluca.ironExpress.exception.SellerException;
import com.aledluca.ironExpress.exception.SellerNotFoundException;
import com.aledluca.ironExpress.models.Customer;
import com.aledluca.ironExpress.models.Product;
import com.aledluca.ironExpress.models.Seller;
import com.aledluca.ironExpress.repository.SellerRepository;
import com.aledluca.ironExpress.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

    // Get all sellers in repo
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
    
    // Update seller data
    public Seller updateSeller(Seller seller, String token) {
        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }
        loginService.checkTokenStatus(token);
        Optional<Seller> opt = sellerRepository.findById(seller.getSellerId()).orElseThrow(()-> new SellerException("Seller not found for this Id: "+seller.getSellerId()));
        Seller existingSeller =
        Seller newSeller = sellerRepository.save(seller);
        return newSeller;
    }

}
