package net.maslyna.message.repository;

import net.maslyna.message.model.entity.UserMessage;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserMessageRepository extends ReactiveCassandraRepository<UserMessage, UUID> {

    @Query("""
            SELECT * FROM t_user_messages AS t
            WHERE (t.sender_id = :user1 OR t.receiver_id = :user1) AND (t.sender_id = :user2 OR t.receiver_id = :user2)
            """)
    Flux<UserMessage> findUserMessagesByOwners(UUID user1, UUID user2);

    @Query("""
            SELECT COUNT(t.user_message_id) FROM t_user_messages AS t
            WHERE (t.sender_id = :user1 OR t.receiver_id = :user1) AND (t.sender_id = :user2 OR t.receiver_id = :user2)
            """)
    Mono<Long> countUserMessagesByOwners(UUID user1, UUID user2);

    @Query("""
            SELECT * FROM t_user_messages AS t
            WHERE (t.sender_id = :user1 OR t.receiver_id = :user1) AND (t.sender_id = :user2 OR t.receiver_id = :user2)
            """)
    Flux<UserMessage> findUserMessagesByOwners(UUID user1, UUID user2, Pageable pageable);
}
