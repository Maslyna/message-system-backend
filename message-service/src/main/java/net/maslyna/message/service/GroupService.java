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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Collection;
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
                    Flux<Void> saveMembers = saveGroupMembers(ownerId, savedGroup, groupSettings.users());
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

    private Flux<Void> saveGroupMembers(final UUID ownerId, final Group group, final Collection<UUID> memberIds) {
        return client.isUsersInContacts(ownerId, memberIds)
                .filter(Tuple2::getT2) //if user is owner friend
                .map(Tuple2::getT1) // get user id
                .flatMap(user -> saveGroupMember(group, user, false));
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
