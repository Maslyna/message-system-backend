package net.maslyna.message.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import net.maslyna.message.mapper.GroupMapper;
import net.maslyna.message.model.dto.GroupDTO;
import net.maslyna.message.model.entity.Group;
import net.maslyna.message.model.request.CreateGroup;
import net.maslyna.message.service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final GroupMapper mapper;

    @GetMapping()
    public Mono<Page<GroupDTO>> groups(@RequestHeader("userId") UUID user,
                                       @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(1000) int size,
                                       @RequestParam(value = "num", defaultValue = "0") @PositiveOrZero int num,
                                       @RequestParam(value = "orderBy", defaultValue = "DESC")
                                       @Pattern(
                                               regexp = "asc|desc",
                                               flags = {Pattern.Flag.CASE_INSENSITIVE},
                                               message = "error.validation.sort.direction.message"
                                       )
                                       String order
    ) {
        return service.getGroups(
                user,
                PageRequest.of(num, size, Sort.Direction.fromString(order))
        ).map(groups -> groups.map(mapper::toDto));
    }

    @PostMapping()
    public Mono<UUID> create(@RequestHeader("userId") UUID user, @Valid @RequestBody CreateGroup settings) {
        return service.create(user, settings).map(Group::getGroupId);
    }
}
