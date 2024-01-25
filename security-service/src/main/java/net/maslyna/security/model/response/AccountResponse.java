package net.maslyna.security.model.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AccountResponse(
        UUID accountId,
        String email
) {
}
