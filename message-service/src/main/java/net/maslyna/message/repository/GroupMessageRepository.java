package net.maslyna.message.repository;

import net.maslyna.message.model.entity.GroupMessage;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface GroupMessageRepository extends ReactiveCassandraRepository<GroupMessage, UUID> {
}
