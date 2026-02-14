package com.ingreedy.core.client;

import com.ingreedy.core.client.dto.OpenAiChatRequest;
import com.ingreedy.core.client.dto.OpenAiChatResponse;
import com.ingreedy.core.client.dto.OpenAiContent;
import com.ingreedy.core.client.dto.OpenAiOutput;
import com.ingreedy.core.client.dto.OpenAiResponseFormat;
import com.ingreedy.core.client.dto.OpenAiText;
import com.ingreedy.core.validator.OpenAiResponseValidator;
import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenAiClient implements LlmClient {
    private final RestClient restClient;
    private final String model;
    private final double temperature;
    private final OpenAiResponseValidator responseValidator;
    private final boolean mockEnabled;
    private final String apiKey;

    private static final String RESPONSES_ENDPOINT = "/v1/responses";

    public OpenAiClient(
            RestClient.Builder restClientBuilder,
            Environment environment,
            OpenAiResponseValidator responseValidator) {
        String baseUrl = environment.getRequiredProperty("llm.openai.base-url");
        this.apiKey = environment.getRequiredProperty("llm.openai.api-key");
        this.model = environment.getRequiredProperty("llm.openai.model");
        this.temperature = environment.getProperty("llm.openai.temperature", Double.class, 0.7);
        this.mockEnabled = environment.getProperty("llm.openai.mock-enabled", Boolean.class, false);

        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.responseValidator = responseValidator;
    }

    @Override
    public String generate(String prompt) {
        System.out.println(this.apiKey);
        OpenAiChatRequest request = buildRequest(prompt);

        OpenAiChatResponse response = mockEnabled ? getMockedResponse()
                : restClient.post()
                        .uri(RESPONSES_ENDPOINT)
                        .body(request)
                        .retrieve()
                        .body(OpenAiChatResponse.class);

        try {
            responseValidator.validate(response);
        } catch (IllegalStateException e) {
            throw new RuntimeException("Invalid response from OpenAI: " + e.getMessage(), e);
        }

        return response.firstOutputText();
    }

    private OpenAiChatRequest buildRequest(String prompt) {
        return new OpenAiChatRequest(
            "You are a helpful culinary assistant.",
            prompt,
            model,
            temperature,
            new OpenAiText(new OpenAiResponseFormat("json_object")));
    }

    private OpenAiChatResponse getMockedResponse() {
        String response = "{\n  \"recommendations\": [\n    {\n      \"title\": \"Wild Mushroom Risotto\",\n      \"nutritionTags\": [\"Vegetarian\", \"Gluten-Free\"],\n      \"ingredients\": [\n        {\"name\": \"Arborio rice\", \"grams\": 200, \"preparation\": \"uncooked\"},\n        {\"name\": \"Mixed mushrooms\", \"grams\": 250, \"preparation\": \"sliced\"},\n        {\"name\": \"Vegetable broth\", \"grams\": 1000, \"preparation\": \"heated\"},\n        {\"name\": \"Parmesan cheese\", \"grams\": 80, \"preparation\": \"grated\"}\n      ],\n      \"recipe\": \"Sauté mushrooms, toast rice, gradually add broth until creamy, stir in Parmesan, serve hot.\"\n    },\n    {\n      \"title\": \"Spinach and Ricotta Stuffed Shells\",\n      \"nutritionTags\": [\"Vegetarian\", \"High-Protein\"],\n      \"ingredients\": [\n        {\"name\": \"Jumbo pasta shells\", \"grams\": 250, \"preparation\": \"uncooked\"},\n        {\"name\": \"Ricotta cheese\", \"grams\": 300, \"preparation\": \"smooth\"},\n        {\"name\": \"Spinach\", \"grams\": 200, \"preparation\": \"chopped\"},\n        {\"name\": \"Tomato sauce\", \"grams\": 700, \"preparation\": \"prepared\"}\n      ],\n      \"recipe\": \"Cook shells, mix ricotta and spinach, stuff shells, cover with sauce, bake 25 min.\"\n    },\n    {\n      \"title\": \"Vegetarian Thai Red Curry\",\n      \"nutritionTags\": [\"Vegetarian\", \"Dairy-Free\"],\n      \"ingredients\": [\n        {\"name\": \"Tofu\", \"grams\": 400, \"preparation\": \"cubed\"},\n        {\"name\": \"Red bell pepper\", \"grams\": 150, \"preparation\": \"sliced\"},\n        {\"name\": \"Coconut milk\", \"grams\": 400, \"preparation\": \"canned\"},\n        {\"name\": \"Red curry paste\", \"grams\": 50, \"preparation\": \"ready-made\"}\n      ],\n      \"recipe\": \"Sauté curry paste, add coconut milk and vegetables, simmer 15 min, serve with rice.\"\n    },\n    {\n      \"title\": \"Eggplant Parmesan\",\n      \"nutritionTags\": [\"Vegetarian\", \"Comfort Food\"],\n      \"ingredients\": [\n        {\"name\": \"Eggplant\", \"grams\": 500, \"preparation\": \"sliced\"},\n        {\"name\": \"Mozzarella cheese\", \"grams\": 200, \"preparation\": \"sliced\"},\n        {\"name\": \"Tomato sauce\", \"grams\": 600, \"preparation\": \"prepared\"},\n        {\"name\": \"Breadcrumbs\", \"grams\": 150, \"preparation\": \"seasoned\"}\n      ],\n      \"recipe\": \"Bread and fry eggplant, layer with sauce and cheese, bake until golden.\"\n    },\n    {\n      \"title\": \"Butternut Squash Ravioli\",\n      \"nutritionTags\": [\"Vegetarian\", \"Seasonal\"],\n      \"ingredients\": [\n        {\"name\": \"Butternut squash\", \"grams\": 600, \"preparation\": \"roasted and mashed\"},\n        {\"name\": \"Ricotta cheese\", \"grams\": 200, \"preparation\": \"smooth\"},\n        {\"name\": \"Pasta sheets\", \"grams\": 400, \"preparation\": \"rolled thin\"},\n        {\"name\": \"Sage\", \"grams\": 10, \"preparation\": \"fresh leaves\"}\n      ],\n      \"recipe\": \"Fill pasta sheets with squash and ricotta, seal ravioli, boil, serve with sage butter.\"\n    },\n    {\n      \"title\": \"Lentil Shepherd’s Pie\",\n      \"nutritionTags\": [\"Vegetarian\", \"High-Fiber\"],\n      \"ingredients\": [\n        {\"name\": \"Lentils\", \"grams\": 250, \"preparation\": \"cooked\"},\n        {\"name\": \"Carrots\", \"grams\": 150, \"preparation\": \"diced\"},\n        {\"name\": \"Peas\", \"grams\": 120, \"preparation\": \"fresh\"},\n        {\"name\": \"Potatoes\", \"grams\": 800, \"preparation\": \"boiled and mashed\"}\n      ],\n      \"recipe\": \"Cook vegetables and lentils, top with mashed potatoes, bake until golden.\"\n    },\n    {\n      \"title\": \"Stuffed Portobello Mushrooms\",\n      \"nutritionTags\": [\"Vegetarian\", \"Low-Carb\"],\n      \"ingredients\": [\n        {\"name\": \"Portobello mushrooms\", \"grams\": 600, \"preparation\": \"stems removed\"},\n        {\"name\": \"Goat cheese\", \"grams\": 200, \"preparation\": \"softened\"},\n        {\"name\": \"Spinach\", \"grams\": 100, \"preparation\": \"chopped\"},\n        {\"name\": \"Cherry tomatoes\", \"grams\": 150, \"preparation\": \"halved\"}\n      ],\n      \"recipe\": \"Fill mushrooms with spinach and cheese, top with tomatoes, bake 15 min.\"\n    },\n    {\n      \"title\": \"Creamy Polenta with Roasted Veggies\",\n      \"nutritionTags\": [\"Vegetarian\", \"Gluten-Free\"],\n      \"ingredients\": [\n        {\"name\": \"Polenta\", \"grams\": 250, \"preparation\": \"uncooked\"},\n        {\"name\": \"Vegetable broth\", \"grams\": 900, \"preparation\": \"boiling\"},\n        {\"name\": \"Zucchini\", \"grams\": 200, \"preparation\": \"sliced\"},\n        {\"name\": \"Red bell pepper\", \"grams\": 150, \"preparation\": \"sliced\"}\n      ],\n      \"recipe\": \"Cook polenta in broth until creamy, top with roasted vegetables, serve hot.\"\n    },\n    {\n      \"title\": \"Chickpea and Spinach Coconut Stew\",\n      \"nutritionTags\": [\"Vegetarian\", \"High-Protein\"],\n      \"ingredients\": [\n        {\"name\": \"Chickpeas\", \"grams\": 400, \"preparation\": \"cooked\"},\n        {\"name\": \"Spinach\", \"grams\": 200, \"preparation\": \"fresh\"},\n        {\"name\": \"Coconut milk\", \"grams\": 400, \"preparation\": \"canned\"},\n        {\"name\": \"Garlic\", \"grams\": 10, \"preparation\": \"minced\"}\n      ],\n      \"recipe\": \"Sauté garlic, add chickpeas and coconut milk, simmer, fold in spinach, serve with rice.\"\n    },\n    {\n      \"title\": \"Four-Cheese Baked Gnocchi\",\n      \"nutritionTags\": [\"Vegetarian\", \"Comfort Food\"],\n      \"ingredients\": [\n        {\"name\": \"Potato gnocchi\", \"grams\": 500, \"preparation\": \"fresh\"},\n        {\"name\": \"Mozzarella\", \"grams\": 150, \"preparation\": \"shredded\"},\n        {\"name\": \"Gorgonzola\", \"grams\": 100, \"preparation\": \"crumbled\"},\n        {\"name\": \"Ricotta\", \"grams\": 200, \"preparation\": \"smooth\"}\n      ],\n      \"recipe\": \"Mix gnocchi with cheeses, bake until bubbly and golden, serve hot.\"\n    }\n  ]\n}";

        return new OpenAiChatResponse(
                List.of(new OpenAiOutput(
                        "msg_001",
                        "message",
                        "assistant",
                        List.of(new OpenAiContent("output_text", response)))));
    }
}
