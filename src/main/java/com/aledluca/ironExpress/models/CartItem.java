package com.aledluca.ironExpress.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartItemId;
    @OneToOne
    @JsonIgnoreProperties(value={
            "productId",
            "seller",
            "quantity"
    })
    private Product cartProduct;
    private Integer cartItemQuantity;

    public CartItem() {
    }

    public CartItem(Product cartProduct, Integer cartItemQuantity) {
        this.cartProduct = cartProduct;
        this.cartItemQuantity = cartItemQuantity;
    }

    public CartItem(Integer cartItemId, Product cartProduct, Integer cartItemQuantity) {
        this.cartItemId = cartItemId;
        this.cartProduct = cartProduct;
        this.cartItemQuantity = cartItemQuantity;
    }

    public Integer getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Integer cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Product getCartProduct() {
        return cartProduct;
    }

    public void setCartProduct(Product cartProduct) {
        this.cartProduct = cartProduct;
    }

    public Integer getCartItemQuantity() {
        return cartItemQuantity;
    }

    public void setCartItemQuantity(Integer cartItemQuantity) {
        this.cartItemQuantity = cartItemQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(cartItemId, cartItem.cartItemId) && Objects.equals(cartProduct, cartItem.cartProduct) && Objects.equals(cartItemQuantity, cartItem.cartItemQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItemId, cartProduct, cartItemQuantity);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId=" + cartItemId +
                ", cartProduct=" + cartProduct +
                ", cartItemQuantity=" + cartItemQuantity +
                '}';
    }
}
