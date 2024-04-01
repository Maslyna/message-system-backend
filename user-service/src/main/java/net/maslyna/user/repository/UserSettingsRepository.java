package net.maslyna.user.repository;

import net.maslyna.user.model.entity.UserSettings;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserSettingsRepository extends R2dbcRepository<UserSettings, UUID> {

    @Query("""
            SELECT * FROM t_user_settings AS us
            WHERE us.user_id = :userId
            """)
    Mono<UserSettings> findByUserId(UUID userId);
}
