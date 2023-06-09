package com.aledluca.ironExpress.repository;

import com.aledluca.ironExpress.models.Customer;
import com.aledluca.ironExpress.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {
    Optional<Seller> findByContactNumber(String contactNumber);
    Optional<Seller> findByEmail(String email);
    Optional<Seller> findByContactNumberOrEmail(String contactNumber, String email);
}
