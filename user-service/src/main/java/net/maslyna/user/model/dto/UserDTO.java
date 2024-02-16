package net.maslyna.user.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDTO(
        String email,
        String username,
        String bio,
        String status,
        LocalDateTime lastLogin,
        LocalDateTime createdAt
) {
}
