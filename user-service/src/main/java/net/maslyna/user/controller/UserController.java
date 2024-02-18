package net.maslyna.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.user.model.dto.RegistrationRequest;
import net.maslyna.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserService userService;

    @PostMapping
    public Mono<ResponseEntity<Void>> create(@Valid @RequestBody RegistrationRequest body) {
        return userService.save(body.userId(), body.email(), body.username())
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> delete(@RequestHeader("userId") UUID authenticatedUser) {
        return userService.delete(authenticatedUser)
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }
}
