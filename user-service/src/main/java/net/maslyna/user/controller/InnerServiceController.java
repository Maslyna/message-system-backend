package net.maslyna.user.controller;

import lombok.RequiredArgsConstructor;
import net.maslyna.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ssc/v1/users")
@Validated
public class InnerServiceController {
    private final UserService userService;

    @GetMapping("/{userId}/exists")
    public Mono<Boolean> exists(@PathVariable("userId") UUID userId) {
        return userService.exists(userId);
    }

    @PostMapping("/exists")
    public Mono<Map<UUID, Boolean>> exists(@RequestBody List<UUID> users) {
        return userService.exists(users);
    }

    @PostMapping(value = "/group-membership-check",
            consumes = MediaType.APPLICATION_NDJSON_VALUE,
            produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Tuple2<UUID, Boolean>> isContacts(@RequestHeader("userId") UUID userId, @RequestBody Flux<UUID> users) {
        return userService.isUsersInContacts(userId, users);
    }

    @GetMapping("/{userId}/permissions/addtogroup")
    public Mono<Boolean> isUserAllowedToAddToContacts(@PathVariable("userId") UUID userId,
                                                      @RequestHeader("userId") UUID authenticated) {
        return userService.isUserInContacts(userId, authenticated);
    }
}
