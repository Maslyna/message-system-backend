package net.maslyna.message.repository;

import net.maslyna.message.model.entity.GroupMember;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface GroupMemberRepository extends ReactiveCassandraRepository<GroupMember, UUID> {
}
