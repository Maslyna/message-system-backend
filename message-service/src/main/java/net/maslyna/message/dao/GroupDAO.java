package net.maslyna.message.dao;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.maslyna.message.exception.GroupNotFoundException;
import net.maslyna.message.model.entity.Group;
import net.maslyna.message.repository.GroupMemberRepository;
import net.maslyna.message.repository.GroupRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
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
                .switchIfEmpty(Mono.error(() -> new GroupNotFoundException("Group with id = %s not found".formatted(groupId))));
    }

    public Mono<Group> save(final @Valid Group group) {
        return groupRepository.save(group);
    }

    public Mono<Void> delete(final UUID groupId) {
        return groupRepository.deleteById(groupId);
    }
}
