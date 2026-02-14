package com.ingreedy.core.client.dto;

public record OpenAiChatRequest(
    String instructions,
    String input,
    String model,
    double temperature,
    OpenAiText text
) {
}
