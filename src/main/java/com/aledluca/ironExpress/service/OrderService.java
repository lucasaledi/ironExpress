package com.aledluca.ironExpress.service;

import com.aledluca.ironExpress.dto.CartDTO;
import com.aledluca.ironExpress.dto.OrderDTO;
import com.aledluca.ironExpress.enums.OrderStatusValues;
import com.aledluca.ironExpress.enums.ProductStatus;
import com.aledluca.ironExpress.exception.LoginException;
import com.aledluca.ironExpress.exception.OrderException;
import com.aledluca.ironExpress.models.CartItem;
import com.aledluca.ironExpress.models.Customer;
import com.aledluca.ironExpress.models.Order;
import com.aledluca.ironExpress.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CartService cartService;


    // Save order
//    public Order saveOrder(OrderDTO orderDTO, String token) throws LoginException, OrderException {
//        Order newOrder= new Order();
//        Customer loggedInCustomer= customerService.getLoggedInCustomerDetails(token);
//        if(loggedInCustomer != null) {
//            //Customer loggedInCustomer= customerService.getLoggedInCustomerDetails(token);
//            newOrder.setCustomer(loggedInCustomer);
//            String usersCardNumber= loggedInCustomer.getCreditCard().getCardNumber();
//            String userGivenCardNumber= orderDTO.getCardNumber().getCardNumber();
//            List<CartItem> productsInCart= loggedInCustomer.getCustomerCart().getCartItems();
//            List<CartItem> productsInOrder = new ArrayList<>(productsInCart);
//            newOrder.setOrderCartItems(productsInOrder);
//            newOrder.setTotal(loggedInCustomer.getCustomerCart().getCartTotal());
//            if(productsInCart.size()!=0) {
//                if((usersCardNumber.equals(userGivenCardNumber))
//                        && (orderDTO.getCardNumber().getExpireDate().equals(loggedInCustomer.getCreditCard().getExpireDate())
//                        && (orderDTO.getCardNumber().getCvv().equals(loggedInCustomer.getCreditCard().getCvv())))) {
//                    //System.out.println(usersCardNumber);
//                    newOrder.setCardNumber(orderDTO.getCardNumber().getCardNumber());
//                    newOrder.setAddress(loggedInCustomer.getAddress().get(orderDTO.getAddressType()));
//                    newOrder.setDate(LocalDate.now());
//                    newOrder.setOrderStatus(OrderStatusValues.SUCCESS);
//                    System.out.println(usersCardNumber);
//                    List<CartItem> cartItemsList= loggedInCustomer.getCustomerCart().getCartItems();
//                    for(CartItem cartItem : cartItemsList ) {
//                        Integer remainingQuantity = cartItem.getCartProduct().getQuantity()-cartItem.getCartItemQuantity();
//                        if(remainingQuantity < 0 || cartItem.getCartProduct().getStatus() == ProductStatus.OUTOFSTOCK) {
//                            CartDTO cartdto = new CartDTO();
//                            cartdto.setProductId(cartItem.getCartProduct().getProductId());
//                            cartService.removeProductFromCart(cartdto, token);
//                            throw new OrderException("Product "+ cartItem.getCartProduct().getProductName() + " OUT OF STOCK");
//                        }
//                        cartItem.getCartProduct().setQuantity(remainingQuantity);
//                        if(cartItem.getCartProduct().getQuantity()==0) {
//                            cartItem.getCartProduct().setStatus(ProductStatus.OUTOFSTOCK);
//                        }
//                    }
//                    cartService.clearCart(token);
//                    //System.out.println(newOrder);
//                    return orderRepository.save(newOrder);
//                } else {
//                    //System.out.println("Not same");
//                    newOrder.setCardNumber(null);
//                    newOrder.setAddress(loggedInCustomer.getAddress().get(orderDTO.getAddressType()));
//                    newOrder.setDate(LocalDate.now());
//                    newOrder.setOrderStatus(OrderStatusValues.PENDING);
//                    cartService.clearCart(token);
//                    return orderRepository.save(newOrder);
//                }
//            } else {
//                throw new OrderException("No products in cart");
//            }
//        } else {
//            throw new LoginException("Invalid session token for customer. Please, make sure to log-in");
//        }
//    }
    public Order saveOrder(OrderDTO orderDTO, Integer userId) throws LoginException, OrderException {
        Order newOrder= new Order();
        Customer loggedInCustomer= customerService.getLoggedInCustomerDetails(userId);
        if(loggedInCustomer != null) {
            //Customer loggedInCustomer= customerService.getLoggedInCustomerDetails(token);
            newOrder.setCustomer(loggedInCustomer);
            String usersCardNumber= loggedInCustomer.getCreditCard().getCardNumber();
            String userGivenCardNumber= orderDTO.getCardNumber().getCardNumber();
            List<CartItem> productsInCart= loggedInCustomer.getCustomerCart().getCartItems();
            List<CartItem> productsInOrder = new ArrayList<>(productsInCart);
            newOrder.setOrderCartItems(productsInOrder);
            newOrder.setTotal(loggedInCustomer.getCustomerCart().getCartTotal());
            if(productsInCart.size()!=0) {
                if((usersCardNumber.equals(userGivenCardNumber))
                        && (orderDTO.getCardNumber().getExpireDate().equals(loggedInCustomer.getCreditCard().getExpireDate())
                        && (orderDTO.getCardNumber().getCvv().equals(loggedInCustomer.getCreditCard().getCvv())))) {
                    //System.out.println(usersCardNumber);
                    newOrder.setCardNumber(orderDTO.getCardNumber().getCardNumber());
                    newOrder.setAddress(loggedInCustomer.getAddress().get(orderDTO.getAddressType()));
                    newOrder.setDate(LocalDate.now());
                    newOrder.setOrderStatus(OrderStatusValues.SUCCESS);
                    //System.out.println(usersCardNumber);
                    List<CartItem> cartItemsList= loggedInCustomer.getCustomerCart().getCartItems();
                    for(CartItem cartItem : cartItemsList ) {
                        Integer remainingQuantity = cartItem.getCartProduct().getQuantity()-cartItem.getCartItemQuantity();
                        if(remainingQuantity < 0 || cartItem.getCartProduct().getStatus() == ProductStatus.OUTOFSTOCK) {
                            CartDTO cartdto = new CartDTO();
                            cartdto.setProductId(cartItem.getCartProduct().getProductId());
                            cartService.removeProductFromCart(cartdto, userId);
                            throw new OrderException("Product "+ cartItem.getCartProduct().getProductName() + " OUT OF STOCK");
                        }
                        cartItem.getCartProduct().setQuantity(remainingQuantity);
                        if(cartItem.getCartProduct().getQuantity()==0) {
                            cartItem.getCartProduct().setStatus(ProductStatus.OUTOFSTOCK);
                        }
                    }
                    cartService.clearCart(userId);
                    //System.out.println(newOrder);
                    return orderRepository.save(newOrder);
                } else {
                    //System.out.println("Not same");
                    newOrder.setCardNumber(null);
                    newOrder.setAddress(loggedInCustomer.getAddress().get(orderDTO.getAddressType()));
                    newOrder.setDate(LocalDate.now());
                    newOrder.setOrderStatus(OrderStatusValues.PENDING);
                    cartService.clearCart(userId);
                    return orderRepository.save(newOrder);
                }
            } else {
                throw new OrderException("No products in cart");
            }
        } else {
            throw new LoginException("Invalid session token for customer. Please, make sure to log-in");
        }
    }

    // Get order by id
    public Order getOrderByOrderId(Integer OrderId) throws OrderException {
        return orderRepository.findById(OrderId).orElseThrow(()-> new OrderException("No order exists with given OrderId "+ OrderId));
    }

    // Get all orders
    public List<Order> getAllOrders() throws OrderException {
        List<Order> orders = orderRepository.findAll();
        if(orders.size() > 0) {
            return orders;
        } else {
            throw new OrderException("No orders found in your account");
        }
    }

    // Cancel order by id
//    public Order cancelOrderByOrderId(Integer OrderId,String token) throws OrderException {
//        Order order= orderRepository.findById(OrderId).orElseThrow(()->new OrderException("No order exists with given OrderId "+ OrderId));
//        if(order.getCustomer().getCustomerId()==customerService.getLoggedInCustomerDetails(token).getCustomerId()) {
//            if(order.getOrderStatus()==OrderStatusValues.PENDING) {
//                order.setOrderStatus(OrderStatusValues.CANCELLED);
//                orderRepository.save(order);
//                return order;
//            } else if(order.getOrderStatus()==OrderStatusValues.SUCCESS) {
//                order.setOrderStatus(OrderStatusValues.CANCELLED);
//                List<CartItem> cartItemsList= order.getOrderCartItems();
//                for(CartItem cartItem : cartItemsList ) {
//                    Integer addedQuantity = cartItem.getCartProduct().getQuantity()+cartItem.getCartItemQuantity();
//                    cartItem.getCartProduct().setQuantity(addedQuantity);
//                    if(cartItem.getCartProduct().getStatus() == ProductStatus.OUTOFSTOCK) {
//                        cartItem.getCartProduct().setStatus(ProductStatus.AVAILABLE);
//                    }
//                }
//                orderRepository.save(order);
//                return order;
//            } else {
//                throw new OrderException("Order was already cancelled");
//            }
//        } else {
//            throw new LoginException("Invalid session token for customer. Please, make sure to log-in");
//        }
//    }
    public Order cancelOrderByOrderId(Integer OrderId) throws OrderException {
        Order order= orderRepository.findById(OrderId).orElseThrow(()->new OrderException("No order exists with given OrderId "+ OrderId));
        if(order.getOrderStatus()==OrderStatusValues.PENDING) {
            order.setOrderStatus(OrderStatusValues.CANCELLED);
            orderRepository.save(order);
            return order;
        } else if(order.getOrderStatus()==OrderStatusValues.SUCCESS) {
            order.setOrderStatus(OrderStatusValues.CANCELLED);
            List<CartItem> cartItemsList= order.getOrderCartItems();
            for(CartItem cartItem : cartItemsList ) {
                Integer addedQuantity = cartItem.getCartProduct().getQuantity()+cartItem.getCartItemQuantity();
                cartItem.getCartProduct().setQuantity(addedQuantity);
                if(cartItem.getCartProduct().getStatus() == ProductStatus.OUTOFSTOCK) {
                    cartItem.getCartProduct().setStatus(ProductStatus.AVAILABLE);
                }
            }
            orderRepository.save(order);
            return order;
        } else {
            throw new OrderException("Order was already cancelled");
        }
    }

    // Update order
//    public Order updateOrderByOrder(OrderDTO orderdto, Integer OrderId,String token) throws OrderException,LoginException {
//        Order existingOrder= orderRepository.findById(OrderId).orElseThrow(()->new OrderException("No order exists with given OrderId "+ OrderId));
//        if(existingOrder.getCustomer().getCustomerId()==customerService.getLoggedInCustomerDetails(token).getCustomerId()) {
//            //existingOrder.setCardNumber(orderdto.getCardNumber().getCardNumber());
//            //existingOrder.setAddress(existingOrder.getCustomer().getAddress().get(orderdto.getAddressType()));
//            Customer loggedInCustomer = customerService.getLoggedInCustomerDetails(token);
//            String usersCardNumber= loggedInCustomer.getCreditCard().getCardNumber();
//            String userGivenCardNumber= orderdto.getCardNumber().getCardNumber();
//            //System.out.println(loggedInCustomer);
//            if((usersCardNumber.equals(userGivenCardNumber))
//                    && (orderdto.getCardNumber().getExpireDate().equals(loggedInCustomer.getCreditCard().getExpireDate())
//                    && (orderdto.getCardNumber().getCvv().equals(loggedInCustomer.getCreditCard().getCvv())))) {
//                existingOrder.setCardNumber(orderdto.getCardNumber().getCardNumber());
//                existingOrder.setAddress(existingOrder.getCustomer().getAddress().get(orderdto.getAddressType()));
//                existingOrder.setOrderStatus(OrderStatusValues.SUCCESS);
//                List<CartItem> cartItemsList= existingOrder.getOrderCartItems();
//                for(CartItem cartItem : cartItemsList ) {
//                    Integer remainingQuantity = cartItem.getCartProduct().getQuantity()-cartItem.getCartItemQuantity();
//                    if(remainingQuantity < 0 || cartItem.getCartProduct().getStatus() == ProductStatus.OUTOFSTOCK) {
//                        CartDTO cartdto = new CartDTO();
//                        cartdto.setProductId(cartItem.getCartProduct().getProductId());
//                        cartService.removeProductFromCart(cartdto, token);
//                        throw new OrderException("Product "+ cartItem.getCartProduct().getProductName() + " OUT OF STOCK");
//                    }
//                    cartItem.getCartProduct().setQuantity(remainingQuantity);
//                    if(cartItem.getCartProduct().getQuantity()==0) {
//                        cartItem.getCartProduct().setStatus(ProductStatus.OUTOFSTOCK);
//                    }
//                }
//                return orderRepository.save(existingOrder);
//            } else {
//                throw new OrderException("Incorrect Card Number Again" + usersCardNumber + userGivenCardNumber);
//            }
//        } else {
//            throw new LoginException("Invalid session token for customer. Please, make sure to log-in");
//        }
//    }
    public Order updateOrderByOrderId(OrderDTO orderdto, Integer OrderId, Integer userId) throws OrderException,LoginException {
        Order existingOrder= orderRepository.findById(OrderId).orElseThrow(()->new OrderException("No order exists with given OrderId "+ OrderId));
        //existingOrder.setCardNumber(orderdto.getCardNumber().getCardNumber());
        //existingOrder.setAddress(existingOrder.getCustomer().getAddress().get(orderdto.getAddressType()));
        //System.out.println(loggedInCustomer);
        existingOrder.setCardNumber(orderdto.getCardNumber().getCardNumber());
        existingOrder.setAddress(existingOrder.getCustomer().getAddress().get(orderdto.getAddressType()));
        existingOrder.setOrderStatus(OrderStatusValues.SUCCESS);
        List<CartItem> cartItemsList= existingOrder.getOrderCartItems();
        for(CartItem cartItem : cartItemsList ) {
            Integer remainingQuantity = cartItem.getCartProduct().getQuantity()-cartItem.getCartItemQuantity();
            if(remainingQuantity < 0 || cartItem.getCartProduct().getStatus() == ProductStatus.OUTOFSTOCK) {
                CartDTO cartdto = new CartDTO();
                cartdto.setProductId(cartItem.getCartProduct().getProductId());
                cartService.removeProductFromCart(cartdto, userId);
                throw new OrderException("Product "+ cartItem.getCartProduct().getProductName() + " OUT OF STOCK");
            }
            cartItem.getCartProduct().setQuantity(remainingQuantity);
            if(cartItem.getCartProduct().getQuantity()==0) {
                cartItem.getCartProduct().setStatus(ProductStatus.OUTOFSTOCK);
            }
        }
        return orderRepository.save(existingOrder);
    }

    // Get all orders by date
    public List<Order> getAllOrdersByDate(LocalDate date) throws OrderException {
        List<Order> listOfOrdersOnTheDay= orderRepository.findByDate(date);
        return listOfOrdersOnTheDay;
    }

    // Get customer by order id
    public Customer getCustomerByOrderId(Integer orderId) throws OrderException {
        Optional<Order> opt= orderRepository.findById(orderId);
        if(opt.isPresent()) {
            Order existingorder= opt.get();
            return orderRepository.getCustomerByOrderId(existingorder.getCustomer().getCustomerId());
        } else {
            throw new OrderException("No order exists with orderId " + orderId);
        }
    }
}
