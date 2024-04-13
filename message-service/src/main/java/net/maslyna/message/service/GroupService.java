package net.maslyna.message.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.message.client.UserServiceClient;
import net.maslyna.message.dao.GroupDAO;
import net.maslyna.message.dao.GroupMembersDAO;
import net.maslyna.message.exception.UnauthorizedGroupAccessException;
import net.maslyna.message.model.entity.Group;
import net.maslyna.message.model.entity.GroupMember;
import net.maslyna.message.model.request.CreateGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupDAO groupDAO;
    private final GroupMembersDAO membersDAO;
    private final UserServiceClient client;

    @Transactional
    public Mono<Group> create(final UUID ownerId, final CreateGroup groupSettings) {
        Group group = Group.builder()
                .creator(ownerId)
                .name(groupSettings.name())
                .description(groupSettings.description())
                .isPublic(groupSettings.isPublic())
                .build();

        return groupDAO.save(group).flatMap(savedGroup -> {
                    Mono<Void> saveOwner = saveGroupOwner(savedGroup, ownerId);
                    Mono<Void> saveMembers = saveGroupMembers(savedGroup, groupSettings.users());
                    return Mono.when(saveOwner, saveMembers);
                })
                .thenReturn(group);
    }

    @Transactional
    public Mono<Void> delete(final UUID ownerId, final UUID groupId) {
        return groupDAO.getGroup(groupId)
                .flatMap(group -> {
                    if (group.getCreator().equals(ownerId)) {
                        return groupDAO.delete(group.getGroupId());
                    }
                    return Mono.error(new UnauthorizedGroupAccessException("this is not group of current user"));
                });
    }

    private Mono<Void> saveGroupOwner(final Group group, final UUID ownerId) {
        return membersDAO.save(
                        GroupMember.builder()
                                .groupId(group.getGroupId())
                                .memberId(ownerId)
                                .roleLevel(Short.MAX_VALUE)
                                .build())
                .then();
    }

    private Mono<Void> saveGroupMembers(final Group group, final Collection<UUID> memberIds) {
        return client.usersExists(memberIds)
                .flatMap(usersExist -> {
                    List<UUID> existingUsers = usersExist.entrySet().stream() //TODO: validation, if user can add another user
                            .filter(Map.Entry::getValue)
                            .map(Map.Entry::getKey)
                            .toList();

                    List<Mono<Void>> savedMembers = existingUsers.stream()
                            .map(memberId -> saveGroupMember(group, memberId, false))
                            .toList();

                    return Mono.when(savedMembers);
                });
    }

    private Mono<Void> saveGroupMember(final Group group, final UUID memberId, final boolean isAdmin) {
        return membersDAO.save(
                        GroupMember.builder()
                                .groupId(group.getGroupId())
                                .memberId(memberId)
                                .roleLevel(isAdmin ? (short) 1 : 0)
                                .build())
                .then();
    }

}
