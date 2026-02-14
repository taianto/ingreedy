package com.ingreedy.core.validator;

import com.ingreedy.core.client.dto.OpenAiChatResponse;
import org.springframework.stereotype.Component;

@Component
public class OpenAiResponseValidator {
    public boolean validate(OpenAiChatResponse response) {
        if (response == null || response.output() == null || response.output().isEmpty()) {
            throw new IllegalStateException("OpenAI response contained no output items.");
        }

        String outputText = response.firstOutputText();
        if (outputText == null || outputText.isBlank()) {
            throw new IllegalStateException("OpenAI response contained no output text.");
        }

        return true;
    }
}
