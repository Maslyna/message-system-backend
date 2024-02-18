package net.maslyna.user.model.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RegistrationRequest(
        @NotNull
        UUID userId,
        @Email
        @NotBlank
        String email,
        @Size(max = 30)
        @NotBlank
        String username
) {
}
