package net.maslyna.user.model.dto;

import lombok.Builder;

@Builder
public record UserDTO(
        String email,
        String username,
        String bio,
        String status,
        String lastLogin,
        String createdAt
) {
}
