package com.ingreedy.core.client.dto;

import java.util.List;

public record OpenAiChatResponse(List<OpenAiOutput> output) {
	public String firstOutputText() {
		if (output == null) {
			return null;
		}

		for (OpenAiOutput item : output) {
			if (item == null || item.content() == null) {
				continue;
			}

			for (OpenAiContent content : item.content()) {
				if (content != null && "output_text".equals(content.type()) && content.text() != null) {
					return content.text();
				}
			}
		}

		return null;
	}
}
