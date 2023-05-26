package com.aledluca.ironExpress.repository;

import com.aledluca.ironExpress.models.Customer;
import com.aledluca.ironExpress.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    public List<Order> findByDate(LocalDate date);

//	@Query("select c.orders from Customer c where c.customerId = customerId")
//	public List<Order> getListOfOrdersByCustomerId(@Param("customerId") Integer customerId);

    @Query("select c from Customer c where c.customerId = customerId")
    public Customer getCustomerByOrderId(@Param("customerId") Integer customerId);

//	public List<Product> getListOfProductsByOrderId(Integer OrderId);

//	@Query("update Order o set o.orderStatus =OrderStatusValues.CANCELLED WHERE o.OrderId=OrderId ")
//	public Order CancelOrderByOrderId(@Param("OrderId") Integer OrderId);


}