package com.bar.sistemabar.config.exception;

import java.time.LocalDateTime;

public record ErroResponseRecord(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}