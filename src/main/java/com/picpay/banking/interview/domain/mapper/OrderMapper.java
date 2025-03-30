package com.picpay.banking.interview.domain.mapper;

import com.picpay.banking.interview.domain.Order;
import com.picpay.banking.interview.dto.order.OrderRequest;
import com.picpay.banking.interview.dto.order.OrderResponse;

import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order toDomain(OrderRequest request) {
        return Order.builder()
                .symbol(request.symbol())
                .quantity(request.quantity())
                .price(request.price())
                .side(request.side())
                .build();
    }

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getSymbol(),
                order.getQuantity(),
                order.getPrice(),
                order.getSide()
        );
    }
}