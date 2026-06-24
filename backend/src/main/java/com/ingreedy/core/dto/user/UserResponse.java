package com.ingreedy.core.dto.user;

import java.time.Instant;

public record UserResponse(
    Long id,
    String email,
    String displayName,
    Instant createdAt,
    Instant updatedAt
) {
}
