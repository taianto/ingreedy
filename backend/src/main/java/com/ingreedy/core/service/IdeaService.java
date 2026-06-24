package com.ingreedy.core.service;

import com.ingreedy.core.client.LlmGateway;
import com.ingreedy.core.dto.idea.IdeaResponse;
import com.ingreedy.core.dto.llm.LlmRecommendationsResponse;
import com.ingreedy.core.factory.RecommendationFactory;
import com.ingreedy.core.model.Recommendation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdeaService {
	private final PromptTemplateService promptTemplateService;
	private final LlmGateway llmGateway;
	private final RecommendationFactory factory;
	private final RecommendationCacheService cacheService;

	public IdeaService(
			PromptTemplateService promptTemplateService,
			LlmGateway llmGateway,
			RecommendationFactory factory,
			RecommendationCacheService cacheService
	) {
		this.promptTemplateService = promptTemplateService;
		this.llmGateway = llmGateway;
		this.factory = factory;
		this.cacheService = cacheService;
	}

	public IdeaResponse generateRecommendations(String idea) {
		String prompt = promptTemplateService.renderIdeaPrompt(idea);

		LlmRecommendationsResponse response =
				llmGateway.generateRecommendations(prompt);

		List<Recommendation> recommendations =
				response.recommendations()
						.stream()
						.map(factory::from)
						.toList();

		cacheService.cacheRecommendations(recommendations);

		return new IdeaResponse(recommendations);
	}
}