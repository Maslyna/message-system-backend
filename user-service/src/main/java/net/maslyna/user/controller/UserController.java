package net.maslyna.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.user.mapper.UserMapper;
import net.maslyna.user.model.dto.RegistrationRequest;
import net.maslyna.user.model.dto.UserDTO;
import net.maslyna.user.service.UserPersistenceService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;


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
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(@Valid @RequestBody RegistrationRequest body, @RequestHeader("userId") UUID authenticatedUser) {
        return userPersistenceService.save(authenticatedUser, body.email(), body.username())
                .then();
    }

    @DeleteMapping
    public Mono<Void> delete(@RequestHeader("userId") UUID authenticatedUser) {
        return userPersistenceService.delete(authenticatedUser);
    }
}
