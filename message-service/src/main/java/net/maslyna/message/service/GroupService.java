package net.maslyna.message.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.message.client.UserServiceClient;
import net.maslyna.message.dao.GroupDAO;
import net.maslyna.message.dao.GroupMembersDAO;
import net.maslyna.message.exception.UnauthorizedGroupAccessException;
import net.maslyna.message.model.entity.Group;
import net.maslyna.message.model.request.CreateGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupDAO groupDAO;
    private final GroupMembersDAO membersDAO;
    private final UserServiceClient client;

    public Mono<Page<Group>> getGroups(final UUID userId, PageRequest pageRequest) {
        return groupDAO.getGroups(userId, pageRequest);
    }

    @Transactional
    public Mono<Group> create(final UUID ownerId, final CreateGroup groupSettings) {
        Group group = Group.builder()
                .creator(ownerId)
                .name(groupSettings.name())
                .description(groupSettings.description())
                .isPublic(groupSettings.isPublic())
                .build();

        return groupDAO.save(group)
                .flatMap(savedGroup -> {
                    Mono<Void> saveOwner = saveGroupOwner(savedGroup, ownerId);
                    Mono<Void> saveMembers = saveGroupMembers(ownerId, savedGroup, Flux.fromIterable(groupSettings.users()));
                    return Mono.when(saveOwner, saveMembers)
                            .thenReturn(savedGroup);
                });
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
        return membersDAO.save(group.getGroupId(), ownerId, Short.MAX_VALUE, true)
                .then();
    }

    private Mono<Void> saveGroupMembers(final UUID ownerId, final Group group, final Flux<UUID> memberIds) {
        final Flux<UUID> members = client.isUsersInContacts(ownerId, memberIds)
                .filter(Tuple2::getT2)
                .map(Tuple2::getT1);

        return membersDAO.saveAllInGroup(group.getGroupId(), members, (short)0, false)
                .then();
    }

}
