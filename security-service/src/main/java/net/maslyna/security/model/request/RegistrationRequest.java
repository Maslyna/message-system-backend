package net.maslyna.security.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @Size(max = 255)
        @Email
        @NotEmpty
        String email,
        @Size(max = 255)
        @NotEmpty
        String password
) {
}
