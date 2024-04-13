package net.maslyna.message.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.message.client.UserServiceClient;
import net.maslyna.message.dao.GroupDAO;
import net.maslyna.message.model.entity.Group;
import net.maslyna.message.model.entity.GroupMember;
import net.maslyna.message.model.entity.MemberRole;
import net.maslyna.message.model.request.CreateGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupDAO groupDAO;
    private final UserServiceClient client;

    @Transactional
    public Mono<Group> create(final UUID ownerId, final CreateGroup groupSettings) {
        Group group = Group.builder()
                .groupId(UUID.randomUUID())
                .creator(ownerId)
                .name(groupSettings.name())
                .description(groupSettings.description())
                .isPublic(groupSettings.isPublic())
                .build();
        Mono<Void> saveGroup = groupDAO.save(group)
                .map(g -> {
                    group.setGroupId(g.getGroupId());
                    return g;
                }).then();
        Mono<Void> saveOwner = groupDAO.saveMember(GroupMember.builder()
                .groupId(group.getGroupId())
                .memberId(ownerId)
                .build()).flatMap(owner -> {
            MemberRole ownerRole = createDefaultRole(group.getGroupId(), ownerId, true);
            return groupDAO.saveRole(ownerRole);
        }).then();
        Mono<Void> saveMembers = client.usersExists(groupSettings.users())
                .flatMap(usersExist -> {
                    List<UUID> existingUsers = usersExist.entrySet().stream()
                            .filter(Map.Entry::getValue) //if user exists
                            .map(Map.Entry::getKey)
                            .toList();

                    List<Mono<Void>> savedMembers = existingUsers.stream()
                            .map(memberId -> {
                                MemberRole role = createDefaultRole(group.getGroupId(), memberId, false);
                                return saveGroupMemberAndRole(group, role, memberId);
                            }).toList();
                    return Mono.when(savedMembers);
                });

        return saveGroup.then(saveOwner)
                .then(saveMembers)
                .thenReturn(group);
    }

    private Mono<Void> saveGroupMemberAndRole(Group group, MemberRole memberRole, UUID memberId) {
        GroupMember groupMember = GroupMember.builder()
                .groupId(group.getGroupId())
                .memberId(memberId)
                .joinedAt(LocalDateTime.now())
                .build();
        Mono<GroupMember> savedMember = groupDAO.saveMember(groupMember);
        Mono<MemberRole> savedRole = groupDAO.saveRole(memberRole);
        return Mono.when(savedMember, savedRole).then();
    }

    private MemberRole createDefaultRole(UUID groupId, UUID memberId, boolean isSuper) {
        return MemberRole.builder()
                .memberId(memberId)
                .groupId(groupId)
                .description("")
                .name(isSuper ? "Admin" : "Member")
                .isSuper(isSuper)
                .build();
    }
}
