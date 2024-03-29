package net.maslyna.message.controller;

import lombok.RequiredArgsConstructor;
import net.maslyna.message.service.GroupService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final GroupService service;

    @RequestMapping("/api/v1/{userId}/exists")
    public Mono<Boolean> exists(@PathVariable("userId")UUID userId) {
        return service.test(userId);
    }
}
