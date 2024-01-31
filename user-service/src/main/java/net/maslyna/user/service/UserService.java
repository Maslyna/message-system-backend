package net.maslyna.user.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.user.mapper.UserMapper;
import net.maslyna.user.model.dto.UserDTO;
import net.maslyna.user.model.entity.User;
import net.maslyna.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final UserRepository repository;

    public Mono<User> save(UserDTO user) {
        return Mono.just(user)
                .map(mapper::userDtoToUser)
                .flatMap(repository::save);
    }
}
