package com.ingreedy.core.dto.llm;

import java.util.List;

public record LlmRecommendationsResponse(
        List<LlmRecommendation> recommendations
) {}