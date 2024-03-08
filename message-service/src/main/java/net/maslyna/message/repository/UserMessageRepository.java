package net.maslyna.message.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserMessageRepository extends ReactiveCassandraRepository<UserMessageRepository, UUID> {
}
