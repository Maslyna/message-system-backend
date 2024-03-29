package net.maslyna.message.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.maslyna.message.model.dto.GroupDTO;
import net.maslyna.message.model.entity.Group;
import net.maslyna.message.model.request.CreateGroup;
import net.maslyna.message.service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/groups")
@Validated
public class GroupController {

    private final GroupService service;

    @GetMapping()
    public Mono<Page<GroupDTO>> groups(@RequestHeader("userId") UUID user) {
        return Mono.empty();
    }

//    @PostMapping()
//    public Mono<UUID> create(@RequestHeader("userId") UUID user, @Valid @RequestBody CreateGroup settings) {
//        return service.create(user, settings).map(Group::getGroupId);
//    }
}
