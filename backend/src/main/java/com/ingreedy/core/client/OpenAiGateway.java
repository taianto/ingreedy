package com.ingreedy.core.client;

import com.ingreedy.core.dto.llm.LlmRecipeResponse;
import com.ingreedy.core.dto.llm.LlmRecommendationsResponse;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.StructuredResponse;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class OpenAiGateway implements LlmGateway {

    private final OpenAIClient client;

    public OpenAiGateway(Environment environment) {
        String apiKey = environment.getProperty("llm.openai.api-key");
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    @Override
    public LlmRecommendationsResponse generateRecommendations(String prompt) {

        var params = ResponseCreateParams.builder()
                .model(ChatModel.GPT_4_1_MINI)
                .input(prompt)
                .text(LlmRecommendationsResponse.class)
                .build();

        StructuredResponse<LlmRecommendationsResponse> response =
                client.responses().create(params);

        return response.output()
                .getFirst()
                .asMessage()
                .content()
                .getFirst()
                .asOutputText();
    }

    @Override
    public String generateRecipe(String prompt) {
        var params = ResponseCreateParams.builder()
                .model(ChatModel.GPT_4_1_MINI)
                .input(prompt)
                .text(LlmRecipeResponse.class)
                .build();

        StructuredResponse<LlmRecipeResponse> response =
                client.responses().create(params);

        return response.output()
                .getFirst()
                .asMessage()
                .content()
                .getFirst()
                .asOutputText()
                .recipe();
    }


}