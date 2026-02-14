package com.ingreedy.core.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class PromptTemplateService {
    private static final String TEMPLATE_PATH = "templates/idea.md";
    private static final String IDEA_PLACEHOLDER = "{{idea}}";

    public String renderIdeaPrompt(String idea) {
        String template = loadTemplate();
        return template.replace(IDEA_PLACEHOLDER, idea.trim());
    }

    private String loadTemplate() {
        try {
            ClassPathResource resource = new ClassPathResource(TEMPLATE_PATH);
            byte[] bytes = resource.getInputStream().readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load prompt template: " + TEMPLATE_PATH, ex);
        }
    }
}
