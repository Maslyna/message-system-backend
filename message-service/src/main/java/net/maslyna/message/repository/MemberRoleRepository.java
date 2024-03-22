package net.maslyna.message.repository;

import net.maslyna.message.model.entity.MemberRole;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MemberRoleRepository extends ReactiveCassandraRepository<MemberRole, UUID> {
    @Query("""
            SELECT * FROM t_group_members_roles AS gm
            WHERE gm.group_id = :groupId AND gm.member_id = :memberId
            """)
    Mono<MemberRole> findMemberBy(final UUID groupId, final UUID memberId);
}
