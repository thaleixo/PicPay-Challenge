package com.picpay.banking.interview.service.impl;

import com.picpay.banking.interview.domain.Order;
import com.picpay.banking.interview.domain.enumeration.Side;
import com.picpay.banking.interview.domain.mapper.OrderMapper;
import com.picpay.banking.interview.domain.repository.OrderRepository;
import com.picpay.banking.interview.dto.order.OrderRequest;
import com.picpay.banking.interview.dto.order.OrderResponse;
import com.picpay.banking.interview.exceptions.InvalidOrderException;
import com.picpay.banking.interview.service.OrderBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderBookServiceImpl implements OrderBookService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public long calculateTotalBuyOrders(List<Order> orderBook) {
        return orderBook.stream()
                .filter(order -> order.getSide() == Side.BUY)
                .count();
    }

    @Override
    public long calculateTotalSellOrders(List<Order> orderBook) {
        return orderBook.stream()
                .filter(order -> order.getSide() == Side.SELL)
                .count();
    }

    @Override
    public double calculateTotalValueOrders(List<Order> orderBook) {
        return orderBook.stream()
                .mapToDouble(order -> order.getPrice().doubleValue())
                .sum();
    }

    @Override
    public long calculateTotalQuantityOrders(List<Order> orderBook) {
        return orderBook.stream()
                .mapToLong(Order::getQuantity)
                .sum();
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersBySymbol(String symbol) {
        return orderRepository.findBySymbol(symbol);
    }

    @Override
    public boolean validateQuantityOrderBook(Order order) {
        return order.getQuantity() >= 2;
    }

    @Override
    public void deleteOrderById(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()){
            orderRepository.delete(order.get());
        }

    }

    @Override
    public boolean existsById(Integer id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Order updateOrder(Order order, OrderRequest request) {
        order.setSymbol(request.symbol());
        order.setQuantity(request.quantity());
        order.setPrice(request.price());
        order.setSide(request.side());
        orderRepository.save(order);
        return order;
    }
}