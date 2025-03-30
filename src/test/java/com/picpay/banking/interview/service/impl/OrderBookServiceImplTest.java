package com.picpay.banking.interview.service.impl;

import com.picpay.banking.interview.domain.Order;
import com.picpay.banking.interview.domain.enumeration.Side;
import com.picpay.banking.interview.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderBookServiceImplTest {

    public static final String GOOG_NS = "GOOG.NS";
    public static final String APPL_NS = "APPL.NS";
    public static final String GS_NS = "GS.NS";

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderBookServiceImpl orderBookService;

    private List<Order> orderBook;

    @BeforeEach
    public void init() {
        Order buyGoogle = Order.builder().id(1).symbol(GOOG_NS).quantity(300).price(new BigDecimal("900.30")).side(Side.BUY).build();
        Order sellGoogle = Order.builder().id(2).symbol(GOOG_NS).quantity(600).price(new BigDecimal("890.30")).side(Side.SELL).build();
        Order buyApple = Order.builder().id(3).symbol(APPL_NS).quantity(400).price(new BigDecimal("552")).side(Side.BUY).build();
        Order sellApple = Order.builder().id(4).symbol(APPL_NS).quantity(200).price(new BigDecimal("550")).side(Side.SELL).build();
        Order buyGS = Order.builder().id(5).symbol(GS_NS).quantity(300).price(new BigDecimal("130")).side(Side.BUY).build();

        orderBook = List.of(buyGoogle, sellGoogle, buyApple, sellApple, buyGS);
    }

    @Test
    @DisplayName("Should calculate total buy orders correctly")
    void shouldNumberOfBuyOrdersEquals() {
        long totalBuyOrder = orderBookService.calculateTotalBuyOrders(orderBook);
        assertEquals(3, totalBuyOrder);
    }

    @Test
    @DisplayName("Should calculate total sell orders correctly")
    void shouldNumberOfSellOrdersEquals() {
        long totalSellOrder = orderBookService.calculateTotalSellOrders(orderBook);
        assertEquals(2, totalSellOrder);
    }

    @Test
    @DisplayName("Should calculate total value of orders correctly")
    void shouldTotalValueOrdersEquals() {
        double totalValueOrders = orderBookService.calculateTotalValueOrders(orderBook);
        assertEquals(3022.6, totalValueOrders);
    }

    @Test
    @DisplayName("Should calculate total quantity of orders correctly")
    void shouldTotalQuantityOrdersEquals() {
        long totalQuantityOrders = orderBookService.calculateTotalQuantityOrders(orderBook);
        assertEquals(1800, totalQuantityOrders);
    }

    @Test
    @DisplayName("Should validate order quantity correctly - valid case")
    void shouldValidateValidOrderQuantity() {
        Order order = orderBook.getFirst();
        assertTrue(orderBookService.validateQuantityOrderBook(order));
    }

    @Test
    @DisplayName("Should validate order quantity correctly - invalid case")
    void shouldInvalidateInvalidOrderQuantity() {
        Order order = orderBook.getFirst();
        order.setQuantity(1);
        assertFalse(orderBookService.validateQuantityOrderBook(order));
    }

    @Test
    @DisplayName("Should create order successfully")
    void shouldCreateOrderSuccessfully() {
        Order order = orderBook.getFirst();

        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderBookService.createOrder(order);
        assertEquals(order, result);
    }

    @Test
    @DisplayName("Should get order by id successfully")
    void shouldGetOrderByIdWhenExists() {
        Order order = orderBook.getLast();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Optional<Order> result = orderBookService.getOrderById(order.getId());
        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }

    @Test
    @DisplayName("Should return empty when order not found")
    void shouldReturnEmptyWhenOrderNotFound() {
        when(orderRepository.findById(orderBook.getFirst().getId())).thenReturn(Optional.empty());

        Optional<Order> result = orderBookService.getOrderById(orderBook.getFirst().getId());
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should get all orders successfully")
    void shouldGetAllOrdersSuccessfully() {
        when(orderRepository.findAll()).thenReturn(orderBook);

        List<Order> result = orderBookService.getAllOrders();
        assertEquals(orderBook.size(), result.size());
    }

    @Test
    @DisplayName("Should get orders by symbol successfully")
    void shouldGetOrdersBySymbolSuccessfully() {
        String symbol = "GOOG.NS";

        List<Order> orders = orderBook.stream()
                .filter(o -> o.getSymbol().equals(symbol))
                .toList();

        when(orderRepository.findBySymbol(symbol)).thenReturn(orders);

        List<Order> result = orderBookService.getOrdersBySymbol(symbol);

        assertEquals(orders.size(), result.size());
    }
}
