package com.ingreedy.core.dto;

import java.util.List;

public record IdeaResponse(
    List<Recommendation> recommendations
) {
}
