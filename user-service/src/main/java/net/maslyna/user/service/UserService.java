package net.maslyna.user.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.user.mapper.UserMapper;
import net.maslyna.user.model.entity.User;
import net.maslyna.user.repository.UserRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final UserRepository repository;
    private final R2dbcEntityTemplate template;

    public Mono<User> save(String email, String username) {
        User user = User.builder()
                .email(email)
                .username(username)
                .build();
        return repository.save(user);
    }

}
