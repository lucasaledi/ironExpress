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
    @PostMapping("/products")
    public ResponseEntity<Product> addProductToCatalogHandler(@RequestHeader("token") String token,
                                                              @Valid @RequestBody Product product) {

        Product prod = productService.addProductToCatalog(token, product);

        return new ResponseEntity<Product>(prod, HttpStatus.ACCEPTED);

    }

    // This method gets the product which needs to be added to the cart returns
    // product

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductFromCatalogByIdHandler(@PathVariable("id") Integer id) {

        Product prod = productService.getProductFromCatalogById(id);

        return new ResponseEntity<Product>(prod, HttpStatus.FOUND);

    }

    // This method will delete the product from catalog and returns the response
    // This will be called only when the product qty will be zero or seller wants to
    // delete for any other reason

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductFromCatalogHandler(@PathVariable("id") Integer id) {

        String res = productService.deleteProductFromCatalog(id);
        return new ResponseEntity<String>(res, HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateProductInCatalogHandler(@Valid @RequestBody Product prod) {

        Product prod1 = productService.updateProductInCatalog(prod);

        return new ResponseEntity<Product>(prod1, HttpStatus.OK);

    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProductsHandler() {

        List<Product> list = productService.getAllProductsInCatalog();

        return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
    }

    //this method gets the products mapped to a particular seller
    @GetMapping("/products/seller/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductsOfSellerHandler(@PathVariable("id") Integer id) {

        List<ProductDTO> list = productService.getAllProductsOfSeller(id);

        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);
    }

    @GetMapping("/products/{catEnum}")
    public ResponseEntity<List<ProductDTO>> getAllProductsInCategory(@PathVariable("catEnum") String catEnum) {
        Category category = Category.valueOf(catEnum.toUpperCase());
        List<ProductDTO> list = productService.getProductsOfCategory(category);
        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);
    }

    @GetMapping("/products/status/{status}")
    public ResponseEntity<List<ProductDTO>> getProductsWithStatusHandler(@PathVariable("status") String status) {
        ProductStatus productStatus = ProductStatus.valueOf(status.toUpperCase());
        List<ProductDTO> list = productService.getProductsOfStatus(productStatus);
        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateQuantityOfProduct(@PathVariable("id") Integer id,@RequestBody ProductDTO prodDto){
        Product prod =   productService.updateProductQuantityWithId(id, prodDto);
        return new ResponseEntity<Product>(prod,HttpStatus.ACCEPTED);
    }

}
