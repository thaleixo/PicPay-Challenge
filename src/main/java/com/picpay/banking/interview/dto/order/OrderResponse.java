package com.picpay.banking.interview.dto.order;

import com.picpay.banking.interview.domain.enumeration.Side;
import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String symbol,
        int quantity,
        BigDecimal price,
        Side side
) {}