package net.maslyna.message.repository;

import net.maslyna.message.model.entity.GroupMember;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface GroupMemberRepository extends ReactiveCassandraRepository<GroupMember, UUID> {

    @Query("""
            SELECT * FROM t_group_members
            WHERE group_id = :groupId
            """)
    Flux<GroupMember> findAllByGroupId(UUID groupId);
}
