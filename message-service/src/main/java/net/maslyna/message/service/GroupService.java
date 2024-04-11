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
    public Mono<Group> create(UUID ownerId, CreateGroup groupSettings) {
        Group group = Group.builder()
                .creator(ownerId)
                .name(groupSettings.name())
                .description(groupSettings.description())
                .isPublic(groupSettings.isPublic())
                .build();

        return client.usersExists(groupSettings.users())
                .flatMap(usersExistMap -> {
                    List<UUID> existingUsers = usersExistMap.entrySet().stream()
                            .filter(Map.Entry::getValue)
                            .map(Map.Entry::getKey)
                            .toList();

                    Mono<Group> savedGroup = groupDAO.save(group);
                    List<Mono<Void>> saveMembers = existingUsers.stream()
                            .map(userId -> saveGroupMemberAndRole(group, userId))
                            .toList();

                    return Mono.when(savedGroup, Mono.when(saveMembers)).then(Mono.just(group));
                });
    }

    private Mono<Void> saveGroupMemberAndRole(Group group, UUID userId) {
        GroupMember groupMember = GroupMember.builder()
                .groupId(group.getGroupId())
                .memberId(userId)
                .joinedAt(LocalDateTime.now())
                .build();
        Mono<GroupMember> savedMember = groupDAO.saveMember(groupMember);

        MemberRole memberRole = MemberRole.builder()
                .groupId(group.getGroupId())
                .memberId(userId)
                .isSuper(false)
                .name("Member")
                .description("")
                .build();
        Mono<MemberRole> savedRole = groupDAO.saveRole(memberRole);

        return Mono.when(savedMember, savedRole).then();
    }
}
