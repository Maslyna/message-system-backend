package net.maslyna.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegistrationRequest(
        @Email
        String email,
        @Size(max = 30)
        String username
) {
}
