package net.maslyna.message.model.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record CreateGroup(
        @Size(min = 0, max = 20)
        String name,
        @Size(max = 100)
        String description,
        boolean isPublic,
        Set<UUID> users
) {
}
