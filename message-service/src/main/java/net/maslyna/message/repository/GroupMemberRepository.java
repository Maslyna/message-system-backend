package net.maslyna.message.repository;

import net.maslyna.message.model.entity.GroupMember;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GroupMemberRepository extends ReactiveCassandraRepository<GroupMember, UUID> {

    @Query("""
            SELECT * FROM t_group_members
            WHERE group_id = :groupId
            """)
    Flux<GroupMember> findAllByGroupId(UUID groupId);

    @Query("""
            SELECT * FROM t_group_members
            WHERE member_id = :memberId
            """)
    Flux<GroupMember> findAllByMemberId(UUID memberId, Pageable pageable);

    @Query("""
            SELECT group_id FROM t_group_members
            WHERE member_id = :memberId
            """)
    Flux<UUID> findAllGroupsByMemberId(UUID memberId, Pageable pageable);

    @Query("""
            SELECT COUNT(*) FROM t_group_members
            WHERE member_id = :memberId
            """)
    Mono<Long> countMemberGroups(UUID memberId);
}
