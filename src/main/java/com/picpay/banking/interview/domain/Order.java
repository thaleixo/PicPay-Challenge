package com.picpay.banking.interview.domain;

import com.picpay.banking.interview.domain.enumeration.Side;

import java.math.BigDecimal;

public record Order (
    String symbol,
    int quantity,
    BigDecimal price,
    Side side
) {

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {

        private String symbol;
        private int quantity;
        private BigDecimal price;
        private Side side;

        public OrderBuilder() {}

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

        public Order build() {
            return new Order(symbol, quantity, price, side);
        }
    }
}
