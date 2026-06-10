package com.ingreedy.core.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record Ingredient(
    @NotBlank(message = "Ingredient name must not be empty.")
    String name,
    @DecimalMin(value = "0.0", message = "Grams must be >= 0.")
    double grams,
    @NotBlank(message = "Preparation must not be empty.")
    String preparation
) {
}
