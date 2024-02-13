package net.maslyna.user.repository;

import net.maslyna.user.model.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
    @Query("""
            SELECT u.* FROM t_users u
                JOIN t_user_contacts c ON u.user_id = c.contact_id
            WHERE c.owner_id = :userId
            """)
    Flux<User> findContactsById(UUID userId);
}
