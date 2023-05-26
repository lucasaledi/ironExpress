package com.aledluca.ironExpress.service;

import com.aledluca.ironExpress.dto.ProductDTO;
import com.aledluca.ironExpress.enums.Category;
import com.aledluca.ironExpress.enums.ProductStatus;
import com.aledluca.ironExpress.exception.CategoryNotFoundException;
import com.aledluca.ironExpress.exception.LoginException;
import com.aledluca.ironExpress.exception.ProductNotFoundException;
import com.aledluca.ironExpress.models.Product;
import com.aledluca.ironExpress.models.Seller;
import com.aledluca.ironExpress.repository.ProductRepository;
import com.aledluca.ironExpress.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private SellerRepository sellerRepository;

    // Add product to catalog
//    public Product addProductToCatalog(String token, Product product) {
//        Product prod = null;
//        Seller seller1 = sellerService.getCurrentlyLoggedInSeller(token);
//        product.setSeller(seller1);
//        Seller Existingseller = sellerService.getSellerByContactNumber(product.getSeller().getContactNumber(), token);
//        Optional<Seller> opt = sellerRepository.findById(Existingseller.getSellerId());
//        if (opt.isPresent()) {
//            Seller seller = opt.get();
//            product.setSeller(seller);
//            prod = productRepository.save(product);
//            seller.getProduct().add(product);
//            sellerRepository.save(seller);
//        } else {
//            prod = productRepository.save(product);
//        }
//        return prod;
//    }
    public Product addProductToCatalog(Product product, Integer userId) {
        Product prod = null;
        Seller seller1 = sellerService.getCurrentlyLoggedInSeller(userId);
        product.setSeller(seller1);
        Seller Existingseller = sellerService.getSellerByContactNumber(product.getSeller().getContactNumber());
        Optional<Seller> opt = sellerRepository.findById(Existingseller.getSellerId());
        if (opt.isPresent()) {
            Seller seller = opt.get();
            product.setSeller(seller);
            prod = productRepository.save(product);
            seller.getProduct().add(product);
            sellerRepository.save(seller);
        } else {
            prod = productRepository.save(product);
        }
        return prod;
    }

    // Get product by id
    public Product getProductFromCatalogById(Integer id) throws ProductNotFoundException {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new ProductNotFoundException("Product not found with given id");
        }
    }

    // Delete product by id
    public String deleteProductFromCatalog(Integer id) throws ProductNotFoundException {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isPresent()) {
            Product prod = opt.get();
            //System.out.println(prod);
            productRepository.delete(prod);
            return "Product deleted from catalog";
        } else {
            throw new ProductNotFoundException("Product not found with given id");
        }
    }

    // Update product
    public Product updateProductInCatalog(Product prod) throws ProductNotFoundException {
        Optional<Product> opt = productRepository.findById(prod.getProductId());
        if (opt.isPresent()) {
            opt.get();
            Product prod1 = productRepository.save(prod);
            return prod1;
        } else {
            throw new ProductNotFoundException("Product not found with given id");
        }
    }

    // Get all products
    public List<Product> getAllProductsInCatalog() {
        List<Product> list = productRepository.findAll();
        if (list.size() > 0) {
            return list;
        } else {
            throw new ProductNotFoundException("No products in catalog");
        }
    }

    // Get all products by category
    public List<ProductDTO> getProductsOfCategory(Category catEnum) {
        List<ProductDTO> list = productRepository.getAllProductsInACategory(catEnum);
        if (list.size() > 0) {
            return list;
        } else {
            throw new CategoryNotFoundException("No products found with category: " + catEnum);
        }
    }

    // Get all products by product status
    public List<ProductDTO> getProductsOfStatus(ProductStatus productStatus) {
        List<ProductDTO> list = productRepository.getProductsWithStatus(productStatus);
        if (list.size() > 0) {
            return list;
        } else {
            throw new ProductNotFoundException("No products found with given status: " + productStatus);
        }
    }

    // Update product quantity by its id
    public Product updateProductQuantityWithId(Integer id, ProductDTO prodDto) {
        Product prod = null;
        Optional<Product> opt = productRepository.findById(id);
        if(opt != null) {
            prod = opt.get();
            prod.setQuantity(prod.getQuantity() + prodDto.getQuantity());
            if(prod.getQuantity()>0) {
                prod.setStatus(ProductStatus.AVAILABLE);
            }
            productRepository.save(prod);
        } else {
            throw new ProductNotFoundException("No product found with this id");
        }
        return prod;
    }

    // Get all products by seller
    public List<ProductDTO> getAllProductsOfSeller(Integer id) {
        List<ProductDTO> list = productRepository.getProductsOfASeller(id);
        if(list.size()>0) {
            return list;
        } else {
            throw new ProductNotFoundException("No products with SellerId: " + id);
        }
    }
}
