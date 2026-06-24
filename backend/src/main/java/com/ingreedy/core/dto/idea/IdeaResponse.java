package com.ingreedy.core.dto.idea;

import com.ingreedy.core.model.Recommendation;

import java.util.List;

public record IdeaResponse(
    List<Recommendation> recommendations
) {
}
