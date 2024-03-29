package net.maslyna.message.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.message.client.UserServiceClient;
import net.maslyna.message.dao.GroupDAO;
import net.maslyna.message.model.entity.Group;
import net.maslyna.message.model.entity.GroupMember;
import net.maslyna.message.model.request.CreateGroup;
import net.maslyna.message.repository.GroupMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupDAO groupDAO;
    private final UserServiceClient client;

//    @Transactional
//    public Mono<Group> create(UUID ownerId, CreateGroup settings) {
//        final Group group = Group.builder()
//                .creator(ownerId)
//                .name(settings.name())
//                .description(settings.description())
//                .isPublic(settings.isPublic())
//                .build();
//        final Flux<GroupMember> members =
//    }

    public Mono<Boolean> test(UUID id) {
        return client.isUserExists(id);
    }
}
