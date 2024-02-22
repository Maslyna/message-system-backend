package net.maslyna.user.model.dto;

import lombok.Builder;

@Builder
public record UserUpdateDTO (
        String email,
        String username,
        String bio,
        String status
) {
}
