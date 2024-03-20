package net.maslyna.message.repository;

import net.maslyna.message.model.entity.MemberRole;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface MemberRoleRepository extends ReactiveCassandraRepository<MemberRole, UUID> {
}
