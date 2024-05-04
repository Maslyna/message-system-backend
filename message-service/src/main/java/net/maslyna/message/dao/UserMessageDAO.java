package net.maslyna.message.dao;

import lombok.RequiredArgsConstructor;
import net.maslyna.message.model.entity.UserMessage;
import net.maslyna.message.repository.UserMessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserMessageDAO {
    private final UserMessageRepository repository;

    @Transactional(readOnly = true)
    public Mono<Page<UserMessage>> getUserMessages(final UUID userId, final UUID otherUserId, Pageable page) {
        return repository.findUserMessagesByOwners(userId, otherUserId, page)
                .collectList()
                .zipWith(repository.countUserMessagesByOwners(userId, otherUserId))
                .map(t -> new PageImpl<>(t.getT1(), page, t.getT2()));
    }

    public Mono<Long> countUserMessages(final UUID userId, final UUID otherUserId) {
        return repository.countUserMessagesByOwners(userId, otherUserId);
    }

    public Flux<UserMessage> getUserMessages(final UUID userId, final UUID otherUserId) {
        return repository.findUserMessagesByOwners(userId, otherUserId);
    }
}
