package com.ingreedy.core.client;

import com.ingreedy.core.dto.IdeaResponse;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.StructuredResponseCreateParams;
import com.openai.models.responses.StructuredResponse;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
public class OpenAiGateway implements LlmGateway {
    private final OpenAIClient client;

    public OpenAiGateway(
            Environment environment
    ) {
        String apiKey = environment.getProperty("llm.openai.api-key");
        this.client = OpenAIOkHttpClient.builder().apiKey(apiKey).build();
    }

    public IdeaResponse generate(String prompt) {
        StructuredResponseCreateParams<IdeaResponse> params = ResponseCreateParams.builder()
                .model(ChatModel.GPT_4_1_MINI)
                .input(prompt)
                .text(IdeaResponse.class)
                .build();

        StructuredResponse<IdeaResponse> response = this.client.responses().create(params);
        return parsed(response);
    }

    private IdeaResponse parsed(StructuredResponse<IdeaResponse> response) {
        return response.output()
                .getFirst()
                .asMessage()
                .content()
                .getFirst()
                .asOutputText();
    }
}
