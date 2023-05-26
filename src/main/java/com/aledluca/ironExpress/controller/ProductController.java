package com.aledluca.ironExpress.controller;

import com.aledluca.ironExpress.dto.ProductDTO;
import com.aledluca.ironExpress.enums.Category;
import com.aledluca.ironExpress.enums.ProductStatus;
import com.aledluca.ironExpress.models.Product;
import com.aledluca.ironExpress.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // this method adds new product to catalog by seller(if seller is new it adds seller as well
    // if seller is already existing products will be mapped to same seller) and returns added product
//    @PostMapping("/products")
//    public ResponseEntity<Product> addProductToCatalogHandler(@RequestHeader("token") String token,
//                                                              @Valid @RequestBody Product product) {
//
//        Product prod = productService.addProductToCatalog(token, product);
//
//        return new ResponseEntity<>(prod, HttpStatus.ACCEPTED);
//
//    }
    @PostMapping("/products/{userId}")
    public ResponseEntity<Product> addProductToCatalogHandler(@Valid @RequestBody Product product, @PathVariable Integer userId) {
        return new ResponseEntity<>(productService.addProductToCatalog(product, userId), HttpStatus.ACCEPTED);
    }

    // This method gets the product which needs to be added to the cart returns product
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductFromCatalogByIdHandler(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(productService.getProductFromCatalogById(id), HttpStatus.FOUND);
    }

    // This method will delete the product from catalog and returns the response
    // This will be called only when the product qty will be zero or seller wants to
    // delete for any other reason
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductFromCatalogHandler(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(productService.deleteProductFromCatalog(id), HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateProductInCatalogHandler(@Valid @RequestBody Product prod) {
        return new ResponseEntity<>(productService.updateProductInCatalog(prod), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProductsHandler() {
        return new ResponseEntity<>(productService.getAllProductsInCatalog(), HttpStatus.OK);
    }

    //this method gets the products mapped to a particular seller
    @GetMapping("/products/seller/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductsOfSellerHandler(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(productService.getAllProductsOfSeller(id), HttpStatus.OK);
    }

    @GetMapping("/products/{catEnum}")
    public ResponseEntity<List<ProductDTO>> getAllProductsInCategory(@PathVariable("catEnum") String catEnum) {
        Category category = Category.valueOf(catEnum.toUpperCase());
        return new ResponseEntity<>(productService.getProductsOfCategory(category), HttpStatus.OK);
    }

    @GetMapping("/products/status/{status}")
    public ResponseEntity<List<ProductDTO>> getProductsWithStatusHandler(@PathVariable("status") String status) {
        ProductStatus productStatus = ProductStatus.valueOf(status.toUpperCase());
        return new ResponseEntity<>(productService.getProductsOfStatus(productStatus), HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateQuantityOfProduct(@PathVariable("id") Integer id,@RequestBody ProductDTO prodDto){
        return new ResponseEntity<>(productService.updateProductQuantityWithId(id, prodDto),HttpStatus.ACCEPTED);
    }

}
