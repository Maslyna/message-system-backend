package net.maslyna.message.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import net.maslyna.message.mapper.MessageMapper;
import net.maslyna.message.model.dto.UserMessageDTO;
import net.maslyna.message.service.UserMessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class UserMessageController {
    private final UserMessageService service;
    private final MessageMapper mapper;

    @GetMapping("/{userId}")
    public Mono<Page<UserMessageDTO>> getPrivateMessages(@RequestHeader("userId") UUID currentUser,
                                                         @PathVariable("userId") UUID user,
                                                         @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(1000) int size,
                                                         @RequestParam(value = "num", defaultValue = "0") @PositiveOrZero int num,
                                                         @RequestParam(value = "orderBy", defaultValue = "DESC")
                                      @Pattern(
                                              regexp = "asc|desc",
                                              flags = {Pattern.Flag.CASE_INSENSITIVE},
                                              message = "error.validation.sort.direction.message"
                                      )
                                      String order,
                                                         @RequestParam(value = "properties", defaultValue = "createdAt") String[] properties
    ) {
        return service.getPrivateMessages(
                currentUser,
                user,
                PageRequest.of(num, size, Sort.Direction.fromString(order), properties)
        ).map(page -> page.map(mapper::toDTO));
    }
}
