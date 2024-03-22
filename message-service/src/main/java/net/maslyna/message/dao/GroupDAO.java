package net.maslyna.message.dao;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.maslyna.message.exception.GroupNotFoundException;
import net.maslyna.message.exception.UserNotFoundException;
import net.maslyna.message.model.entity.Group;
import net.maslyna.message.model.entity.GroupMember;
import net.maslyna.message.model.entity.MemberRole;
import net.maslyna.message.repository.GroupMemberRepository;
import net.maslyna.message.repository.GroupRepository;
import net.maslyna.message.repository.MemberRoleRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.lang.StringTemplate.STR;

@Component
@RequiredArgsConstructor
@Validated
public class GroupDAO {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository memberRepository;
    private final MemberRoleRepository roleRepository;

    public Mono<Group> getGroup(final UUID groupId) {
        return groupRepository.findById(groupId)
                .switchIfEmpty(Mono.error(() -> new GroupNotFoundException(STR."Group with id = \{groupId} not found")));
    }

    public Mono<Group> save(final @Valid Group group) {
        return groupRepository.save(group);
    }

    public Flux<GroupMember> getMembers(final UUID groupId) {
        return groupRepository.existsById(groupId)
                .flatMapMany(exists -> {
                    if (!exists) {
                        return Flux.error(() -> new GroupNotFoundException(STR."Group with id = \{groupId} not found"));
                    }
                    return memberRepository.findAllByGroupId(groupId);
                });
    }

    public Mono<MemberRole> getRole(final UUID groupId, final UUID memberId) {
        return roleRepository.findMemberBy(groupId, memberId)
                .switchIfEmpty(
                        Mono.error(() -> new UserNotFoundException(
                                STR."User with id = \{memberId} or group with id = \{groupId} not found")
                        ));
    }
}
