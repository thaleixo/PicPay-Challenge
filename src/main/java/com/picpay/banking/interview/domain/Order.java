package com.picpay.banking.interview.domain;

import com.picpay.banking.interview.domain.enumeration.Side;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 10)
    private String symbol;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 4)
    private Side side;

    @Builder
    public Order(Integer id, String symbol, int quantity, BigDecimal price, Side side) {
        this.id = id;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.side = side;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private Integer id;
        private String symbol;
        private int quantity;
        private BigDecimal price;
        private Side side;

        public OrderBuilder symbol(final String symbol) {
            this.symbol = symbol;
            return this;
        }

        public OrderBuilder quantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderBuilder price(final BigDecimal price) {
            this.price = price;
            return this;
        }

        public OrderBuilder side(final Side side) {
            this.side = side;
            return this;
        }

        public OrderBuilder id(final Integer id) {
            this.id = id;
            return this;
        }

        public Order build() {
            return new Order(id, symbol, quantity, price, side);
        }
    }
}