package com.ingreedy.core.service;

import com.ingreedy.core.client.LlmGateway;
import com.ingreedy.core.dto.recipe.RecipeResponse;
import com.ingreedy.core.model.Recommendation;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeService {
    private static final boolean ENABLE_MOCK = true;

    private final RecommendationCacheService cacheService;
    private final PromptTemplateService promptTemplateService;
    private final LlmGateway llmGateway;

    public RecipeService(
            RecommendationCacheService cacheService,
            PromptTemplateService promptTemplateService,
            LlmGateway llmGateway
    ) {
        this.cacheService = cacheService;
        this.promptTemplateService = promptTemplateService;
        this.llmGateway = llmGateway;
    }

    public RecipeResponse generateRecipe(String ideaUuid) {
        Recommendation recommendation = cacheService.getRecommendation(ideaUuid)
                .orElseThrow(() -> new IllegalArgumentException("Recommendation not found for ID: " + ideaUuid));

        if (recommendation.recipe().isPresent()) {
            return new RecipeResponse(recommendation.recipe().get());
        }

        String recipeText;
        String prompt = promptTemplateService.renderRecipePrompt(recommendation.ingredients());
        recipeText = llmGateway.generateRecipe(prompt);

        Recommendation updatedRecommendation = new Recommendation(
                recommendation.id(),
                recommendation.title(),
                recommendation.nutritionTags(),
                recommendation.ingredients(),
                Optional.of(recipeText)
        );

        cacheService.cacheRecommendation(updatedRecommendation);

        return new RecipeResponse(recipeText);
    }
}
