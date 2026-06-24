package com.ingreedy.core.dto.llm;

import java.util.List;

public record LlmRecommendation(
        String title,
        String description,
        List<String> nutritionTags,
        List<String> ingredients
) {}