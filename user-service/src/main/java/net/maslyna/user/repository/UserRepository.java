package net.maslyna.user.repository;

import net.maslyna.user.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends R2dbcRepository<User, UUID> {
    @Query("""
            SELECT u.* FROM t_users u
                JOIN t_user_contacts c ON u.user_id = c.contact_id
            WHERE c.owner_id = :userId
            """)
    Flux<User> findContactsById(UUID userId);

    @Query("""
            SELECT u.* FROM t_users u
                JOIN t_user_contacts c ON u.user_id = c.contact_id
            WHERE c.owner_id = :userId
            """)
    Flux<User> findContactsPageById(Pageable pageable, UUID userId);

    @Query("""
            SELECT COUNT(u.*) FROM t_users u
                JOIN t_user_contacts c ON u.user_id = c.contact_id
            WHERE c.owner_id = :userId
            """)
    Mono<Long> countContactsById(UUID userId);

    @Query("""
            SELECT COUNT(u.*) > 1 FROM t_users u
            WHERE u.email = :email OR u.username = :username
            """)
    Mono<Boolean> existsUserEmailOrUsernameBy(String email, String username);
}
