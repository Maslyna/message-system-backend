package net.maslyna.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.user.mapper.UserMapper;
import net.maslyna.user.model.dto.RegistrationRequest;
import net.maslyna.user.service.UserPersistenceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


//TODO: make this controller accessible only for security service
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {
    private final UserMapper mapper;
    private final UserPersistenceService userPersistenceService;

    @PostMapping
    public Mono<RegistrationRequest> create(@Valid @RequestBody RegistrationRequest body) {
        return userPersistenceService.save(body.email(), body.username())
                .map(mapper::userToUserDto);
    }
}
