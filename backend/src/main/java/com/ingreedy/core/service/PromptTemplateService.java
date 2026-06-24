package com.ingreedy.core.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class PromptTemplateService {
    private static final String IDEA_TEMPLATE_PATH = "templates/idea.md";
    private static final String RECIPE_TEMPLATE_PATH = "templates/recipe.md";
    private static final String IDEA_PLACEHOLDER = "{{idea}}";
    private static final String INGREDIENTS_PLACEHOLDER = "{{ingredients}}";

    public String renderIdeaPrompt(String idea) {
        String template = loadTemplate(IDEA_TEMPLATE_PATH);
        return template.replace(IDEA_PLACEHOLDER, idea.trim());
    }

    public String renderRecipePrompt(List<String> ingredients) {
        String template = loadTemplate(RECIPE_TEMPLATE_PATH);
        String joinedIngredients = String.join(", ", ingredients);
        return template.replace(INGREDIENTS_PLACEHOLDER, joinedIngredients.trim());
    }

    private String loadTemplate(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            byte[] bytes = resource.getInputStream().readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load prompt template: " + path, ex);
        }
    }
}
