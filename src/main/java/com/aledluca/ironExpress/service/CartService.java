package com.aledluca.ironExpress.service;

import com.aledluca.ironExpress.dto.CartDTO;
import com.aledluca.ironExpress.exception.CartItemNotFound;
import com.aledluca.ironExpress.exception.CustomerNotFoundException;
import com.aledluca.ironExpress.models.Cart;
import com.aledluca.ironExpress.models.CartItem;
import com.aledluca.ironExpress.models.UserSession;
import com.aledluca.ironExpress.models.Customer;
import com.aledluca.ironExpress.repository.CartRepository;
import com.aledluca.ironExpress.repository.CustomerRepository;
import com.aledluca.ironExpress.repository.ProductRepository;
import com.aledluca.ironExpress.repository.SessionRepository;
import com.aledluca.ironExpress.exception.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoginLogoutService loginService;
    @Autowired
    private ProductRepository productRepository;


    // Add product to cart
    public Cart addProductToCart(CartDTO cartDto, String token) {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Customer> opt = customerRepository.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");
        Customer existingCustomer = opt.get();
        Cart customerCart = existingCustomer.getCustomerCart();
        List<CartItem> cartItems = customerCart.getCartItems();
        CartItem item = cartItemService.addItemToCart(cartDto);
        if(cartItems.size() == 0) {
            cartItems.add(item);
            customerCart.setCartTotal(item.getCartProduct().getPrice());
        } else {
            boolean flag = false;
            for(CartItem c: cartItems) {
                if(c.getCartProduct().getProductId() == cartDto.getProductId()) {
                    c.setCartItemQuantity(c.getCartItemQuantity() + 1);
                    customerCart.setCartTotal(customerCart.getCartTotal() + c.getCartProduct().getPrice());
                    flag = true;
                }
            }
            if(!flag) {
                cartItems.add(item);
                customerCart.setCartTotal(customerCart.getCartTotal() + item.getCartProduct().getPrice());
            }
        }
        return cartRepository.save(existingCustomer.getCustomerCart());
    }


    // Get product from cart
    public Cart getCartProduct(String token) {
        //System.out.println("Inside get cart");
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Customer> opt = customerRepository.findById(user.getUserId());
        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");
        Customer existingCustomer = opt.get();
//		System.out.println(existingCustomer);
//		System.out.println(existingCustomer.getCustomerCart());
//		System.out.println("Here reached");
        Integer cartId = existingCustomer.getCustomerCart().getCartId();
        Optional<Cart> optCart= cartRepository.findById(cartId);
        if(optCart.isEmpty()) {
            throw new CartItemNotFound("Cart not found for given id: " + cartId);
        }
//		return optCart.get().getProducts();
        return optCart.get();
//		return cart.getProducts();
    }


    // Remove product from cart
    public Cart removeProductFromCart(CartDTO cartDto, String token) {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Customer> opt = customerRepository.findById(user.getUserId());
        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");
        Customer existingCustomer = opt.get();
        Cart customerCart = existingCustomer.getCustomerCart();
        List<CartItem> cartItems = customerCart.getCartItems();
        if(cartItems.size() == 0) {
            throw new CartItemNotFound("Cart is empty");
        }
        boolean flag = false;
        for(CartItem c: cartItems) {
            //System.out.println("Item " + c.getCartProduct());
            if(c.getCartProduct().getProductId() == cartDto.getProductId()) {
                c.setCartItemQuantity(c.getCartItemQuantity() - 1);
                customerCart.setCartTotal(customerCart.getCartTotal() - c.getCartProduct().getPrice());
                if(c.getCartItemQuantity() == 0) {
                    cartItems.remove(c);
                    return cartRepository.save(customerCart);
                }
                flag = true;
            }
        }
        if(!flag) {
            throw new CartItemNotFound("Product not found in cart");
        }
        if(cartItems.size() == 0) {
            cartRepository.save(customerCart);
            throw new CartItemNotFound("Cart is currently empty");
        }
        return cartRepository.save(customerCart);
    }


    // Method to clear entire car
    public Cart clearCart(String token) {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }
        loginService.checkTokenStatus(token);
        UserSession user = sessionRepository.findByToken(token).get();
        Optional<Customer> opt = customerRepository.findById(user.getUserId());
        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");
        Customer existingCustomer = opt.get();
        Cart customerCart = existingCustomer.getCustomerCart();
        if(customerCart.getCartItems().size() == 0) {
            throw new CartItemNotFound("Cart already empty");
        }
        List<CartItem> emptyCart = new ArrayList<>();
        customerCart.setCartItems(emptyCart);
        customerCart.setCartTotal(0.0);
        return cartRepository.save(customerCart);
    }

}
