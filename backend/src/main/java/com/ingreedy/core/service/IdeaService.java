package com.ingreedy.core.service;

import com.ingreedy.core.client.LlmGateway;
import com.ingreedy.core.dto.IdeaResponse;
import org.springframework.stereotype.Service;

@Service
public class IdeaService {
	private final PromptTemplateService promptTemplateService;
	private final LlmGateway llmGateway;

	public IdeaService(
		PromptTemplateService promptTemplateService,
		LlmGateway llmGateway
	) {
		this.promptTemplateService = promptTemplateService;
		this.llmGateway = llmGateway;
	}

	public IdeaResponse generateRecommendations(String idea) {
		String prompt = promptTemplateService.renderIdeaPrompt(idea);
		return llmGateway.generate(prompt);
	}
}
