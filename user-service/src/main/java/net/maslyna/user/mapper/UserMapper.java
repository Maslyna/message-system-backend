package net.maslyna.user.mapper;

import net.maslyna.user.model.dto.UserDTO;
import net.maslyna.user.model.dto.UserSettingsDTO;
import net.maslyna.user.model.entity.User;
import net.maslyna.user.model.entity.UserSettings;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO userToUserDto(User user) {
        if (user == null)
            return null;

        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .bio(user.getBio())
                .lastLogin(String.valueOf(user.getLastLogin()))
                .build();
    }

    public UserDTO userToUserDtoWithPrivacySettings(User user, UserSettings settings) {
        if (user == null || settings == null)
            return null;

        return UserDTO.builder()
                .username(user.getUsername())
                .email(settings.isPublicEmail() ? user.getEmail() : "")
                .status(settings.isPublicStatus() ? user.getEmail() : "")
                .bio(settings.isPublicBio() ? user.getBio() : "")
                .lastLogin(settings.isPublicLastLogin() ? String.valueOf(user.getLastLogin()) : null)
                .createdAt(user.getCreatedAt().toString())
                .build();
    }

    public UserSettingsDTO userSettingsToDto(UserSettings settings) {
        if (settings == null)
            return null;

        return UserSettingsDTO.builder()
                .isPublicEmail(settings.isPublicEmail())
                .isPublicContacts(settings.isPublicContacts())
                .isPublicBio(settings.isPublicBio())
                .isPublicStatus(settings.isPublicStatus())
                .receiveMessages(settings.isReceiveMessages())
                .build();
    }
}
