package net.maslyna.message.repository;

import net.maslyna.message.model.entity.Group;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface GroupRepository extends ReactiveCassandraRepository<Group, UUID> {
}
