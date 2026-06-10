package com.ingreedy.core.client;

import com.ingreedy.core.dto.IdeaResponse;

public interface LlmGateway {
    IdeaResponse generate(String prompt);
}
