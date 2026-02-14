package com.ingreedy.core.dto;

import jakarta.validation.constraints.NotBlank;

public record IdeaRequest(
	@NotBlank(message = "Idea must not be empty.")
	String idea
) {
}
