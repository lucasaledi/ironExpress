package com.aledluca.ironExpress.controller;

import com.aledluca.ironExpress.dto.SellerUpdateDTO;
import com.aledluca.ironExpress.dto.SessionDTO;
import com.aledluca.ironExpress.dto.SellerDTO;
import com.aledluca.ironExpress.models.Seller;
import com.aledluca.ironExpress.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {

    @Autowired
    private SellerService sellerService;

    // Add seller
    @PostMapping("/addSeller")
    public ResponseEntity<Seller> addSellerHandler(@Valid @RequestBody Seller seller){
        Seller addseller = sellerService.addSeller(seller);
        //System.out.println("Seller"+ seller);
        return new ResponseEntity<>(addseller, HttpStatus.CREATED);
    }

    // Get the list of all sellers
    @GetMapping("/sellers")
    public ResponseEntity<List<Seller>> getAllSellerHandler(@RequestHeader("token") String token){
        return new ResponseEntity<>(sellerService.getAllSellers(token), HttpStatus.OK);
    }

    // Get seller by id
    @GetMapping("/seller/{id}")
    public ResponseEntity<Seller> getSellerByIdHandler(@PathVariable("id") Integer id){
        return new ResponseEntity<>(sellerService.getSellerById(id), HttpStatus.OK);
    }

    // Get seller by contact number
    @GetMapping("/seller")
    public ResponseEntity<Seller> getSellerByMobileHandler(@RequestParam("contactNumber") String contactNumber, @RequestHeader("token") String token){
        return new ResponseEntity<>(sellerService.getSellerByContactNumber(contactNumber, token), HttpStatus.OK);
    }

    // Get currently logged in seller
    @GetMapping("/seller/current")
    public ResponseEntity<Seller> getLoggedInSellerHandler(@RequestHeader("token") String token){
        return new ResponseEntity<>(sellerService.getCurrentlyLoggedInSeller(token), HttpStatus.OK);
    }

    // Update seller
    @PutMapping("/seller")
    public ResponseEntity<Seller> updateSellerHandler(@Valid @RequestBody SellerUpdateDTO sellerUpdate, @RequestHeader("token") String token){
        return new ResponseEntity<>(sellerService.updateSeller(sellerUpdate, token), HttpStatus.ACCEPTED);
    }

    // Update seller contact number
    @PutMapping("/seller/update/contactNumber")
    public ResponseEntity<Seller> updateSellerContactNumberHandler(@Valid @RequestBody SellerUpdateDTO sellerDTO, @RequestHeader("token") String token){
        return new ResponseEntity<>(sellerService.updateSellerContactNumberOrEmail(sellerDTO, token),HttpStatus.ACCEPTED);
    }

    // Update seller password
    @PutMapping("/seller/update/password")
    public ResponseEntity<SessionDTO> updateSellerPasswordHandler(@Valid @RequestBody SellerDTO sellerDTO, @RequestHeader("token") String token){
        return new ResponseEntity<>(sellerService.updateSellerPassword(sellerDTO, token), HttpStatus.ACCEPTED);
    }

    // Delete seller
    @DeleteMapping("/seller")
    public ResponseEntity<SessionDTO> deleteSellerHandler(@Valid @RequestBody SellerDTO sellerDTO, @RequestHeader("token") String token){
        return new ResponseEntity<>(sellerService.deleteSeller(sellerDTO, token), HttpStatus.ACCEPTED);
    }

}
