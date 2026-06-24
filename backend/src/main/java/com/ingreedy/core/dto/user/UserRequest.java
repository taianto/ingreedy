package com.ingreedy.core.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
    @NotBlank(message = "Email must not be empty.")
    @Email(message = "Email must be a valid email address.")
    @Size(max = 320, message = "Email must be at most 320 characters.")
    String email,

    @NotBlank(message = "Display name must not be empty.")
    @Size(max = 100, message = "Display name must be at most 100 characters.")
    String displayName
) {
}
