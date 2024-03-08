package net.maslyna.message.repository;

import net.maslyna.message.model.entity.UserMessage;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserMessageRepository extends ReactiveCassandraRepository<UserMessage, UUID> {
}
