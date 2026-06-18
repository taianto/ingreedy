package com.ingreedy.core.client;

import com.ingreedy.core.dto.llm.LlmRecommendationsResponse;

public interface LlmGateway {
    LlmRecommendationsResponse generateRecommendations(String prompt);
}