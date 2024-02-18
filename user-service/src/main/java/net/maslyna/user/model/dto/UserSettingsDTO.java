package net.maslyna.user.model.dto;

import lombok.Builder;

@Builder
public record UserSettingsDTO(
        Boolean isPublicEmail,
        Boolean isPublicStatus,
        Boolean isPublicBio,
        Boolean isPublicLastLogin,
        Boolean isPublicContacts,
        Boolean receiveMessages
) {
}
