package com.ingreedy.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingreedy.core.dto.IdeaResponse;
import com.ingreedy.core.dto.Recommendation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class LlmResponseParser {
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public LlmResponseParser(ObjectMapper objectMapper, Validator validator) {
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    public IdeaResponse parse(String rawResponse) {
        String cleaned = stripMarkdownFence(rawResponse);
        JsonNode root;
        try {
            root = objectMapper.readTree(cleaned);
        } catch (JsonProcessingException ex) {
            return new IdeaResponse(Collections.emptyList(),
                List.of("LLM response was not valid JSON."),
                0);
        }

        JsonNode recommendationsNode = root.path("recommendations");
        if (!recommendationsNode.isArray()) {
            return new IdeaResponse(Collections.emptyList(),
                List.of("LLM response did not contain recommendations array."),
                0);
        }

        List<Recommendation> recommendations = new ArrayList<>();
        int filteredCount = 0;

        for (JsonNode recommendationNode : recommendationsNode) {
            try {
                Recommendation recommendation = objectMapper.convertValue(recommendationNode, Recommendation.class);
                Set<ConstraintViolation<Recommendation>> violations = validator.validate(recommendation);

                if (violations.isEmpty()) {
                    recommendations.add(recommendation);
                } else {
                    filteredCount++;
                }
            } catch (Exception ex) {
                filteredCount++;
            }
        }

        List<String> warnings = filteredCount > 0
            ? List.of("Filtered " + filteredCount + " malformed recommendation(s) from LLM output.")
            : Collections.emptyList();

        return new IdeaResponse(recommendations, warnings, filteredCount);
    }

    private String stripMarkdownFence(String content) {
        if (content == null) {
            return "";
        }

        String trimmed = content.trim();
        if (trimmed.startsWith("```") && trimmed.endsWith("```")) {
            String withoutStart = trimmed.replaceFirst("^```[a-zA-Z]*", "");
            return withoutStart.replaceFirst("```$", "").trim();
        }

        return trimmed;
    }
}
