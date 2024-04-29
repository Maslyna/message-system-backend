package net.maslyna.message.model.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record GroupDTO (
    UUID groupId,
    UUID creator,
    String name,
    String description,
    boolean isPublic,
    LocalDate createdAt
) {
}
