package com.picpay.banking.interview.service;

import com.picpay.banking.interview.domain.Order;
import com.picpay.banking.interview.dto.order.OrderRequest;
import com.picpay.banking.interview.dto.order.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface OrderBookService {
    long calculateTotalBuyOrders(List<Order> orderBook);
    long calculateTotalSellOrders(List<Order> orderBook);
    double calculateTotalValueOrders(List<Order> orderBook);
    long calculateTotalQuantityOrders(List<Order> orderBook);
    Order createOrder(Order order);
    Optional<Order> getOrderById(Integer id);
    List<Order> getAllOrders();
    List<Order> getOrdersBySymbol(String symbol);
    boolean validateQuantityOrderBook(Order order);
    void deleteOrderById(Integer id);
    boolean existsById(Integer id);
    Order updateOrder(Order order, OrderRequest request);
}
