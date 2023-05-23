package com.aledluca.ironExpress.repository;

import java.util.Optional;

import com.aledluca.ironExpress.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByContactNumber(String contactNumber);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByContactNumberOrEmail(String contactNumber, String email);

}
