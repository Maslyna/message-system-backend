package net.maslyna.security.router.request;

import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @Size(max = 255)
        String email,
        @Size(max = 255)
        String password
) {
}
