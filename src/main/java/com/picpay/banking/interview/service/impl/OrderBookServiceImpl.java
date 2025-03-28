package com.picpay.banking.interview.service.impl;

import com.picpay.banking.interview.domain.Order;
import com.picpay.banking.interview.service.OrderBookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderBookServiceImpl implements OrderBookService {

    @Override
    public long calculateTotalBuyOrders(List<Order> orderBook) {
        return 0;
    }

    @Override
    public long calculateTotalSellOrders(List<Order> orderBook) {
        return 0;
    }

    @Override
    public double calculateTotalValueOrders(
            List<Order> orderBook) {
        return 0;
    }

    @Override
    public long calculateTotalQuantityOrders(
            List<Order> orderBook) {
        return 0;
    }
}
