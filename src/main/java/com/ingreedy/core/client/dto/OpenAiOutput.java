package com.ingreedy.core.client.dto;

import java.util.List;

public record OpenAiOutput(
    String id,
    String type,
    String role,
    List<OpenAiContent> content
) {
}
