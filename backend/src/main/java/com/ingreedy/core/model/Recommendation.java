package com.ingreedy.core.model;

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
