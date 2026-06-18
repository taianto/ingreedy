package com.ingreedy.core.factory;

import com.ingreedy.core.dto.llm.LlmRecommendation;
import com.ingreedy.core.model.Recommendation;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationFactory {

    public Recommendation from(LlmRecommendation llm) {
        return new Recommendation(
                UUID.randomUUID().toString(),
                llm.title(),
                llm.nutritionTags(),
                llm.ingredients(),
                Optional.empty()
        );
    }
}