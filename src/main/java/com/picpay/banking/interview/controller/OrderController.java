package com.picpay.banking.interview.controller;

import com.picpay.banking.interview.domain.Order;
import com.picpay.banking.interview.domain.mapper.OrderMapper;
import com.picpay.banking.interview.dto.error.ErrorResponse;
import com.picpay.banking.interview.dto.order.OrderRequest;
import com.picpay.banking.interview.dto.order.OrderResponse;
import com.picpay.banking.interview.exceptions.InvalidOrderException;
import com.picpay.banking.interview.exceptions.OrderNotFoundException;
import com.picpay.banking.interview.service.OrderBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderBookService orderService;
    private final OrderMapper orderMapper;


    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        Order order = orderMapper.toDomain(request);
        if (!orderService.validateQuantityOrderBook(order)) {
            throw new InvalidOrderException("O pedido deve conter pelo menos 2 itens.");
        }
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(orderMapper.toResponse(createdOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Integer id) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido com ID " + id + " não encontrado"));

        return ResponseEntity.ok(orderMapper.toResponse(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        if (orders.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<OrderResponse> responses = orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<List<OrderResponse>> getOrdersBySymbol(@PathVariable String symbol) {
        List<Order> orders = orderService.getOrdersBySymbol(symbol);

        if (orders.isEmpty()) {
            throw new OrderNotFoundException("Nenhum pedido com o símbolo " + symbol + " encontrado");
        }

        List<OrderResponse> response = orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Integer id) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isEmpty()) {
            throw new OrderNotFoundException("Nenhum pedido com o id " + id + " encontrado");
        }

        orderService.deleteOrderById(id);
        return ResponseEntity.ok().body("Pedido com o id " + id + " Deletado com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Integer id,
                                                     @Valid @RequestBody OrderRequest request) {
        Order existingOrder = orderService.getOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Nenhum pedido com o id " + id + " encontrado"));

        Order updatedOrder = orderService.updateOrder(existingOrder, request);
        return ResponseEntity.ok(orderMapper.toResponse(updatedOrder));
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOrder(InvalidOrderException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("INVALID_ORDER", ex.getMessage()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("ORDER_NOT_FOUND", ex.getMessage()));
    }
}