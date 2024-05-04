package net.maslyna.message.dao;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.maslyna.message.exception.GroupNotFoundException;
import net.maslyna.message.model.entity.Group;
import net.maslyna.message.repository.GroupMemberRepository;
import net.maslyna.message.repository.GroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Component
@RequiredArgsConstructor
@Validated
public class GroupDAO {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository memberRepository;

    public Mono<Group> getGroup(final UUID groupId) {
        return groupRepository.findById(groupId)
                .switchIfEmpty(
                        Mono.error(() -> new GroupNotFoundException("Group with id = %s not found".formatted(groupId)))
                );
    }

    @Transactional(readOnly = true)
    public Mono<Page<Group>> getGroups(final UUID userId, final Pageable pageable) {
        Flux<UUID> groupIds = memberRepository.findAllGroupsByMemberId(userId, pageable);
        Flux<Group> groups = groupRepository.findAllById(groupIds);

        return groups.collectList()
                .zipWith(memberRepository.countMemberGroups(userId))
                .map(t -> new PageImpl<>(t.getT1(), pageable, t.getT2()));
    }

    public Mono<Group> save(final @Valid Group group) {
        return groupRepository.save(group);
    }

    public Mono<Void> delete(final UUID groupId) {
        return groupRepository.deleteById(groupId);
    }


}
