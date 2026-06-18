package com.ingreedy.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

public record Recommendation(
    String id,
    String title,
    List<String> nutritionTags,
    List<String> ingredients,
    Optional<String> recipe
) {
}
