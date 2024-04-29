package net.maslyna.message.dao;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.maslyna.message.model.entity.GroupMember;
import net.maslyna.message.repository.GroupMemberRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GroupMembersDAO {
    private final GroupMemberRepository repository;

    public Mono<GroupMember> save(final @Valid GroupMember member) {
        return repository.save(member);
    }

    public Flux<GroupMember> save(final Iterable<GroupMember> members) {
        return repository.saveAll(members);
    }

    public Flux<GroupMember> getMembers(final UUID groupId) {
        return repository.findAllByGroupId(groupId);
    }

    public Flux<GroupMember> saveAll(Publisher<GroupMember> members) {
        return repository.saveAll(members);
    }

    public Mono<GroupMember> save(UUID groupId, UUID memberId, short roleLvl, boolean isSuper) {
        final GroupMember member = GroupMember.builder()
                .groupId(groupId)
                .memberId(memberId)
                .roleLevel(roleLvl)
                .isSuper(isSuper)
                .build();

        return repository.save(member);
    }

    public Flux<GroupMember> saveAllInGroup(UUID groupId, Flux<UUID> members, short roleLvl, boolean isSuper) {
        final Flux<GroupMember> entities = members.map(
                member -> GroupMember.builder()
                        .groupId(groupId)
                        .memberId(member)
                        .roleLevel(roleLvl)
                        .isSuper(isSuper)
                        .build()
        );

        return repository.saveAll(entities);
    }
}
