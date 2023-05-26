package com.aledluca.ironExpress.controller;

import com.aledluca.ironExpress.dto.OrderDTO;
import com.aledluca.ironExpress.models.Customer;
import com.aledluca.ironExpress.models.Order;
import com.aledluca.ironExpress.repository.OrderRepository;
import com.aledluca.ironExpress.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/place")
    public ResponseEntity<Order> addOrder(@Valid @RequestBody OrderDTO orderDTO, @RequestHeader("token") String token){
        return new ResponseEntity<>(orderService.saveOrder(orderDTO,token), HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrdersByOrderId(@PathVariable("orderId") Integer orderId) {
        return orderService.getOrderByOrderId(orderId);
    }

    @DeleteMapping("/orders/{orderId}")
    public Order cancelOrderByOrderId(@PathVariable("orderId") Integer orderId,@RequestHeader("token") String token){
        return orderService.cancelOrderByOrderId(orderId,token);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Order> updateOrderByOrder(@Valid @RequestBody OrderDTO orderdto, @PathVariable("orderId") Integer orderId,@RequestHeader("token") String token){
        Order updatedOrder= orderService.updateOrderByOrder(orderdto,orderId,token);
        return new ResponseEntity<Order>(updatedOrder,HttpStatus.ACCEPTED);
    }

    @GetMapping("/orders/by/date")
    public List<Order> getOrdersByDate(@RequestParam("date") String date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate ld = LocalDate.parse(date,dtf);
        return orderService.getAllOrdersByDate(ld);
    }

    @GetMapping("/customer/{orderId}")
    public Customer getCustomerDetailsByOrderId(@PathVariable("orderId") Integer orderId) {
        return orderService.getCustomerByOrderId(orderId);
    }

}
