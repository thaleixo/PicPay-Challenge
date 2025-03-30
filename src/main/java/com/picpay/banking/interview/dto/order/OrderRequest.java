package com.picpay.banking.interview.dto.order;

import com.picpay.banking.interview.domain.enumeration.Side;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record OrderRequest(
        @NotBlank(message = "Simbolo não pode ser vazio")
        @Size(max = 10, message = "Simbolo deve ter no máximo 10 caracteres")
        String symbol,

        int quantity,

        @Digits(integer = 15, fraction = 4, message = "Preço deve ter até 4 casas decimais")
        BigDecimal price,

        @NotNull(message = "Side (BUY/SELL) é obrigatório")
        Side side
) {}