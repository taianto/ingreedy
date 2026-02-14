package com.ingreedy.core.service;

import com.ingreedy.core.client.LlmClient;
import com.ingreedy.core.dto.IdeaResponse;
import org.springframework.stereotype.Service;

@Service
public class IdeaService {
	private final PromptTemplateService promptTemplateService;
	private final LlmClient llmClient;
	private final LlmResponseParser llmResponseParser;

	public IdeaService(
		PromptTemplateService promptTemplateService,
		LlmClient llmClient,
		LlmResponseParser llmResponseParser
	) {
		this.promptTemplateService = promptTemplateService;
		this.llmClient = llmClient;
		this.llmResponseParser = llmResponseParser;
	}

	public IdeaResponse generateRecommendations(String idea) {
		String prompt = promptTemplateService.renderIdeaPrompt(idea);
		String rawResponse = llmClient.generate(prompt);
		return llmResponseParser.parse(rawResponse);
	}
}
