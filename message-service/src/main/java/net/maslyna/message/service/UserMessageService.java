package net.maslyna.message.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.message.dao.UserMessageDAO;
import net.maslyna.message.model.entity.UserMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserMessageService {
    private final UserMessageDAO dao;

    public Mono<Page<UserMessage>> getPrivateMessages(UUID currentUser, UUID user, PageRequest pageRequest) {
        return dao.getUserMessages(currentUser, user, pageRequest);
    }
}
