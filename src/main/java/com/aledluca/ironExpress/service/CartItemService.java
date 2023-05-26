package com.aledluca.ironExpress.service;

import com.aledluca.ironExpress.dto.CartDTO;
import com.aledluca.ironExpress.enums.ProductStatus;
import com.aledluca.ironExpress.exception.ProductNotFoundException;
import com.aledluca.ironExpress.models.CartItem;
import com.aledluca.ironExpress.models.Product;
import com.aledluca.ironExpress.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {
    @Autowired
    private ProductRepository productRepository;

    public CartItem addItemToCart(CartDTO cartdto) {
        Product existingProduct = productRepository.findById(cartdto.getProductId()).orElseThrow( () -> new ProductNotFoundException("Product Not found"));
        if(existingProduct.getStatus().equals(ProductStatus.OUTOFSTOCK) || existingProduct.getQuantity() == 0) {
            throw new ProductNotFoundException("Product OUT OF STOCK");
        }
        CartItem newItem = new CartItem();
        newItem.setCartItemQuantity(1);
        newItem.setCartProduct(existingProduct);
        return newItem;
    }


/*	public CartItem addItemToCart(CartDTO cartdto) {
		Product existingProduct = productRepository.findById(cartdto.getProductId()).orElseThrow( () -> new ProductException("Product Not found"));
		Optional<Product> opt = productRepository.findById(cartdto.getProductId());
		if(opt.isEmpty())
			throw new ProductNotFoundException("Product not found");
		Product existingProduct = opt.get();
		CartItem newItem = new CartItem();
		newItem.setCartProduct(existingProduct);
		newItem.setCartItemQuantity(1);
		return newItem;
	}
 */
}
