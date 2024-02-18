package net.maslyna.user.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.user.exception.UserAlreadyExists;
import net.maslyna.user.exception.UserNotFoundException;
import net.maslyna.user.model.entity.User;
import net.maslyna.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SettingService settingService;

    @Transactional
    public Mono<User> save(final UUID id, final String email, final String username) {
        if (id == null)
            return Mono.error(new IllegalArgumentException("id must be specified"));
        if (!StringUtils.hasText(email) || !StringUtils.hasText(username))
            return Mono.error(new IllegalArgumentException("email or username must contain text"));

        return userRepository.existsByIdOrUserEmailOrUsernameBy(id, email, username)
                .flatMap(exists -> {
                    if (exists)
                        return Mono.error(new UserAlreadyExists(HttpStatus.CONFLICT, "user with this email or username already exists"));

                    final User user = User.builder()
                            .id(id)
                            .email(email)
                            .username(username)
                            .build();

                    return userRepository.save(user)
                            .flatMap(savedUser -> settingService.save(id)
                                    .thenReturn(savedUser));
                });
    }

    public Mono<User> getUser(final UUID id) {
        if (id == null)
            return Mono.error(new IllegalArgumentException("id must not be null"));

        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("user with id = %s not found".formatted(id))));
    }

    public Flux<User> getUserContacts(final UUID id) {
        if (id == null)
            return Flux.error(new IllegalArgumentException("id must not be null"));

        return userRepository.findContactsById(id);
    }

    public Mono<Page<User>> getUserContacts(final UUID id, final Pageable page) {
        if (id == null || page == null)
            return Mono.error(new IllegalArgumentException("id or page must not be null"));

        return userRepository.findContactsPageById(page, id)
                .collectList()
                .zipWith(userRepository.countContactsById(id))
                .map(tuple -> new PageImpl<>(tuple.getT1(), page, tuple.getT2()));
    }

    public Mono<Boolean> isUserInContacts(final UUID ownerId, final UUID userId) {
        return userRepository.isUserInContacts(ownerId, userId);
    }

    @Transactional
    public Mono<Void> delete(final UUID authenticatedUser) {
        return userRepository.deleteById(authenticatedUser)
                .then(settingService.delete(authenticatedUser)).then();
    }


}
