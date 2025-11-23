package com.shopify.ecommerce.service;

import com.shopify.ecommerce.exception.OrderException;
import com.shopify.ecommerce.model.Address;
import com.shopify.ecommerce.model.Order;
import com.shopify.ecommerce.model.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, Address shippingAddress) throws OrderException;


    Order findOrderById(Long orderId) throws OrderException;


    List<Order> userOderHistory(Long userId) ;


    Order placeOrder(Long orderId) throws OrderException;

    Order confirmOrder(Long orderId) throws OrderException;

    Order shippedOrder(Long orderId) throws OrderException;

    Order deliveredOrder(Long orderId) throws OrderException;


    Order cancelOrder(Long orderId) throws OrderException;

    List<Order>getAllOrders();


    void deleteOrder(Long orderId) throws OrderException;
}
