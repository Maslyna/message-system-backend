package net.maslyna.security.client.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserRegistrationDto (
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
