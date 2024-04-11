package net.maslyna.user.controller;

import lombok.RequiredArgsConstructor;
import net.maslyna.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ssc/v1/users")
@Validated
public class SecretSquirrelController {
    private final UserService userService;

    @GetMapping("/{userId}/exists")
    public Mono<Boolean> exists(@PathVariable("userId") UUID userId) {
        return userService.exists(userId);
    }

    @GetMapping("/exists")
    public Mono<Map<UUID, Boolean>> exists(@RequestBody List<UUID> users) {
        return userService.exists(users);
    }
}
