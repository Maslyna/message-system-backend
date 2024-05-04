package net.maslyna.message.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserMessageDTO (
        UUID messageId,
        UUID sender,
        UUID receiver,
        String content,
        boolean viewed,
        boolean edited,
        LocalDateTime createdAt
) {
}
