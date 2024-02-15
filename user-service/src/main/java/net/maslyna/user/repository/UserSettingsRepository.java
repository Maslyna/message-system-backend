package net.maslyna.user.repository;

import net.maslyna.user.model.entity.UserSettings;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface UserSettingsRepository extends R2dbcRepository<UserSettings, UUID> {
}
