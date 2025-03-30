package com.picpay.banking.interview.dto.error;

public record ErrorResponse(
        String error,
        String message
) {}