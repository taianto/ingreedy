package com.ingreedy.core.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record Recommendation(
    @NotBlank(message = "Title must not be empty.")
    String title,

    @NotEmpty(message = "Nutrition tags must not be empty.")
    List<@NotBlank(message = "Nutrition tag must not be blank.") String> nutritionTags,

    @NotEmpty(message = "Ingredients must not be empty.")
    List<@NotBlank(message = "Ingredient must not be blank.") String> ingredients
) {
}
