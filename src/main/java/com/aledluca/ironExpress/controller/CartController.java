package com.aledluca.ironExpress.controller;

import com.aledluca.ironExpress.dto.CartDTO;
import com.aledluca.ironExpress.models.Cart;
import com.aledluca.ironExpress.repository.CartRepository;
import com.aledluca.ironExpress.repository.CustomerRepository;
import com.aledluca.ironExpress.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CustomerRepository customerRepository;


    // Add product to cart
    @PostMapping(value = "/cart/add")
    public ResponseEntity<Cart> addProductToCartHandler(@RequestBody CartDTO cartdto , @RequestHeader("token")String token){
        return new ResponseEntity<>(cartService.addProductToCart(cartdto, token), HttpStatus.CREATED);
    }

    //	Get cart products
    @GetMapping(value = "/cart")
    public ResponseEntity<Cart> getCartProductHandler(@RequestHeader("token")String token){
        return new ResponseEntity<>(cartService.getCartProduct(token), HttpStatus.ACCEPTED);
    }

    // Remove product from cart
    @DeleteMapping(value = "/cart")
    public ResponseEntity<Cart> removeProductFromCartHandler(@RequestBody CartDTO cartdto , @RequestHeader("token")String token){
        return new ResponseEntity<>(cartService.removeProductFromCart(cartdto, token), HttpStatus.OK);
    }

    // Clear cart
    @DeleteMapping(value = "/cart/clear")
    public ResponseEntity<Cart> clearCartHandler(@RequestHeader("token") String token){
        return new ResponseEntity<>(cartService.clearCart(token), HttpStatus.ACCEPTED);
    }


}
