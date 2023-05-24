package com.aledluca.ironExpress.repository;

import com.aledluca.ironExpress.dto.ProductDTO;
import com.aledluca.ironExpress.enums.Category;
import com.aledluca.ironExpress.enums.ProductStatus;
import com.aledluca.ironExpress.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    /*@Query(value = """
            SELECT NEW com.aledluca.ironExpress.dto.ProductDTO(
                p.productName
                ,p.manufacturer
                ,p.price
                ,p.quantity)
            FROM Product p
            WHERE p.category=:catEnum
            ;""")

     */
    @Query("select new com.aledluca.ironExpress.dto.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity) "
            + "from Product p where p.category=:catEnum")
    public List<ProductDTO> getAllProductsInACategory(@Param("catEnum") Category catEnum);

    /*@Query(value = """
            SELECT NEW com.aledluca.ironExpress.dto.ProductDTO(
                p.productName
                ,p.manufacturer
                ,p.price
                ,p.quantity)
                FROM Product p
                WHERE p.status=:status
                ;""")

     */
    @Query("select new com.aledluca.ironExpress.dto.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity) "
            + "from Product p where p.status=:status")
    public List<ProductDTO> getProductsWithStatus(@Param("status") ProductStatus status);

    /*@Query(value = """
            SELECT NEW com.aledluca.ironExpress.dto.ProductDTO(
                p.productName
                ,p.manufacturer
                ,p.price
                ,p.quantity)
                FROM Product p
                WHERE p.seller.sellerId=:id
                ;""")

     */
    @Query("select new com.aledluca.ironExpress.dto.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity) "
            + "from Product p where p.seller.sellerId=:id")
    public List<ProductDTO> getProductsOfASeller(@Param("id") Integer id);

}
