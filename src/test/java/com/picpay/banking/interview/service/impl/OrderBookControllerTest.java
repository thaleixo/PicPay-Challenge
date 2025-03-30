package com.picpay.banking.interview.controller;

import com.picpay.banking.interview.domain.Order;
import com.picpay.banking.interview.domain.enumeration.Side;
import com.picpay.banking.interview.domain.mapper.OrderMapper;
import com.picpay.banking.interview.dto.error.ErrorResponse;
import com.picpay.banking.interview.dto.order.OrderRequest;
import com.picpay.banking.interview.dto.order.OrderResponse;
import com.picpay.banking.interview.exceptions.InvalidOrderException;
import com.picpay.banking.interview.exceptions.OrderNotFoundException;
import com.picpay.banking.interview.service.OrderBookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderBookService orderService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderController orderController;

    private Order createTestOrder() {
        return Order.builder()
                .id(1)
                .symbol("GOOG")
                .quantity(10)
                .price(new BigDecimal("100"))
                .side(Side.BUY)
                .build();
    }

    @Test
    @DisplayName("Should create order and return 200 OK")
    void shouldCreateOrderSuccessfully() {
        OrderRequest request = new OrderRequest("GOOG", 10, new BigDecimal("100"), Side.BUY);
        Order order = createTestOrder();
        OrderResponse response = new OrderResponse(1, "GOOG", 10, new BigDecimal("100"), Side.BUY);

        when(orderMapper.toDomain(request)).thenReturn(order);
        when(orderMapper.toResponse(order)).thenReturn(response);
        when(orderService.validateQuantityOrderBook(order)).thenReturn(true);
        when(orderService.createOrder(order)).thenReturn(order);

        ResponseEntity<OrderResponse> result = orderController.createOrder(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().id());
    }

    @Test
    @DisplayName("Should throw InvalidOrderException when quantity is invalid")
    void shouldThrowInvalidOrderExceptionWhenQuantityInvalid() {
        OrderRequest request = new OrderRequest("GOOG", 1, new BigDecimal("100"), Side.BUY);
        Order order = createTestOrder();
        order.setQuantity(1);

        when(orderMapper.toDomain(request)).thenReturn(order);
        when(orderService.validateQuantityOrderBook(order)).thenReturn(false);

        InvalidOrderException exception = assertThrows(InvalidOrderException.class,
                () -> orderController.createOrder(request));

        assertEquals("O pedido deve conter pelo menos 2 itens.", exception.getMessage());
    }

    @Test
    @DisplayName("Should return order when exists")
    void shouldReturnOrderWhenExists() {
        Order order = createTestOrder();
        OrderResponse expectedResponse = new OrderResponse(
                order.getId(),
                order.getSymbol(),
                order.getQuantity(),
                order.getPrice(),
                order.getSide()
        );

        when(orderService.getOrderById(order.getId())).thenReturn(Optional.of(order));
        when(orderMapper.toResponse(order)).thenReturn(expectedResponse);

        ResponseEntity<OrderResponse> response = orderController.getOrderById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    @DisplayName("Should throw OrderNotFoundException when order not found")
    void shouldThrowOrderNotFoundExceptionWhenOrderDoesNotExist() {
        when(orderService.getOrderById(1)).thenReturn(Optional.empty());

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class,
                () -> orderController.getOrderById(1));

        assertEquals("Pedido com ID 1 não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Should return all orders")
    void shouldReturnAllOrders() {
        Order order = createTestOrder();
        OrderResponse orderResponse = new OrderResponse(1, "GOOG", 10, new BigDecimal("100"), Side.BUY);

        when(orderService.getAllOrders()).thenReturn(List.of(order));
        when(orderMapper.toResponse(order)).thenReturn(orderResponse);

        ResponseEntity<List<OrderResponse>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals(orderResponse, response.getBody().get(0));
    }

    @Test
    @DisplayName("Should return empty list when no orders exist")
    void shouldReturnEmptyListWhenNoOrdersExist() {
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());

        ResponseEntity<List<OrderResponse>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    @DisplayName("Should delete order and return no content")
    void shouldDeleteOrderSuccessfully() {
        Order order = createTestOrder();
        when(orderService.getOrderById(1)).thenReturn(Optional.of(order));

        ResponseEntity<?> response = orderController.deleteOrderById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService).deleteOrderById(1);
    }

    @Test
    @DisplayName("Should throw OrderNotFoundException when deleting non-existent order")
    void shouldThrowOrderNotFoundExceptionWhenDeletingNonExistentOrder() {
        when(orderService.getOrderById(1)).thenReturn(Optional.empty());

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class,
                () -> orderController.deleteOrderById(1));

        assertEquals("Nenhum pedido com o id 1 encontrado", exception.getMessage());
    }

}