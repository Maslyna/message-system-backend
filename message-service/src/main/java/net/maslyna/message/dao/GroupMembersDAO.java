package net.maslyna.message.dao;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.maslyna.message.model.entity.GroupMember;
import net.maslyna.message.repository.GroupMemberRepository;
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
}
