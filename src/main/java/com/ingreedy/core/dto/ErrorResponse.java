package com.ingreedy.core.dto;

public record ErrorResponse(
    String code,
    String message
) {
}
