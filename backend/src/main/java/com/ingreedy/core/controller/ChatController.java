package com.ingreedy.core.controller;

import com.ingreedy.core.dto.idea.IdeaRequest;
import com.ingreedy.core.dto.idea.IdeaResponse;
import com.ingreedy.core.dto.recipe.RecipeRequest;
import com.ingreedy.core.dto.recipe.RecipeResponse;
import com.ingreedy.core.service.IdeaService;
import com.ingreedy.core.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final IdeaService ideaService;
    private final RecipeService recipeService;

    public ChatController(IdeaService ideaService,  RecipeService recipeService) {
        this.ideaService = ideaService;
        this.recipeService = recipeService;
    }

    @PostMapping("/idea")
    public ResponseEntity<IdeaResponse> idea(@Valid @RequestBody IdeaRequest request) {
        IdeaResponse response = ideaService.generateRecommendations(request.idea());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/recipe")
    public ResponseEntity<RecipeResponse> recipe(@Valid @RequestBody RecipeRequest request) {
        RecipeResponse response = recipeService.generateRecipe(request.ideaUuid());
        return ResponseEntity.ok(response);
    }
}