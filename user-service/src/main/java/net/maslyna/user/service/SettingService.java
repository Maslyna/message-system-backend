package net.maslyna.user.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.user.exception.UserNotFoundException;
import net.maslyna.user.model.dto.UserSettingsDTO;
import net.maslyna.user.model.entity.UserSettings;
import net.maslyna.user.repository.UserSettingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final UserSettingsRepository repository;

    public Mono<UserSettings> save(final UserSettings settings) {
        return repository.save(settings);
    }

    public Mono<UserSettings> save(final UUID userId) {
        final UserSettings settings = new UserSettings();
        settings.setId(userId);
        settings.setNew(true);
        return repository.save(settings);
    }

    public Mono<UserSettings> getSettings(final UUID userId) {
        if (userId == null)
            return Mono.error(new IllegalArgumentException("userId must not be null"));

        return repository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("user with userId = %s not found".formatted(userId))));
    }

    @Transactional
    public Mono<UserSettings> updateSettings(final UUID id, final UserSettingsDTO dto) {
        if (id == null)
            return Mono.error(new IllegalArgumentException("id must not be null"));
        if (dto == null)
            return Mono.error(new IllegalArgumentException("settings must not be null"));

        return this.getSettings(id)
                .flatMap(settings -> {
                    if (dto.isPublicEmail() != null) {
                        settings.setPublicEmail(dto.isPublicEmail());
                    } else if (dto.isPublicContacts() != null) {
                        settings.setPublicContacts(dto.isPublicContacts());
                    } else if (dto.isPublicStatus() != null) {
                        settings.setPublicStatus(dto.isPublicStatus());
                    } else if (dto.isPublicBio() != null) {
                        settings.setPublicBio(dto.isPublicBio());
                    } else if (dto.receiveMessages() != null) {
                        settings.setReceiveMessages(dto.receiveMessages());
                    } else if (dto.isPublicLastLogin() != null) {
                        settings.setPublicLastLogin(dto.isPublicLastLogin());
                    }

                    return repository.save(settings);
                });
    }

    public Mono<Void> delete(final UUID userId) {
        return repository.deleteById(userId);
    }
}
