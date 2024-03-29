package net.maslyna.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.user.mapper.UserMapper;
import net.maslyna.user.model.dto.RegistrationRequest;
import net.maslyna.user.model.dto.UserDTO;
import net.maslyna.user.model.dto.UserUpdateDTO;
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
    private final UserMapper mapper;

    @PostMapping
    public Mono<ResponseEntity<Void>> create(@Valid @RequestBody RegistrationRequest body) {
        return userService.save(body.userId(), body.email(), body.username())
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> delete(@RequestHeader("userId") UUID userId) {
        return userService.delete(userId)
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }

    @PutMapping
    public Mono<ResponseEntity<UserDTO>> update(@RequestHeader("userId") UUID userId,
                                                @Valid @RequestBody UserUpdateDTO body) {
        return userService.update(userId, body)
                .map(mapper::userToUserDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK));
    }

    @GetMapping("/{userId}/exists")
    public Mono<Boolean> exists(@PathVariable("userId") UUID userId) {
        return userService.exists(userId);
    }
}
