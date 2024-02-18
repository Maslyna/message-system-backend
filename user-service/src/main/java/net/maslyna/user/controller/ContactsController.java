package net.maslyna.user.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.user.exception.ContactsClosedException;
import net.maslyna.user.model.entity.User;
import net.maslyna.user.service.SettingService;
import net.maslyna.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ContactsController {
    private final UserService userService;
    private final SettingService settingService;

    @GetMapping("/{userId}/contacts")
    public Mono<Page<UUID>> contacts(@PathVariable("userId") UUID userId,
                                     @RequestHeader("userId") UUID authenticatedUser,
                                     @RequestParam(value = "size", defaultValue = "5") @Min(1) @Max(1000) Integer pageSize,
                                     @RequestParam(value = "page", defaultValue = "0") @PositiveOrZero Integer pageNum,
                                     @RequestParam(value = "orderBy", defaultValue = "DESC")
                                     @Pattern(
                                             regexp = "asc|desc",
                                             flags = {Pattern.Flag.CASE_INSENSITIVE}
                                     )
                                     String order,
                                     @RequestParam(name = "sortBy", defaultValue = "createdAt") String... sortBy
    ) {
        if (userId.equals(authenticatedUser))
            return personalContacts(authenticatedUser, pageSize, pageNum, order, sortBy);

        return settingService.getSettings(userId)
                .flatMap(settings -> {
                    if (!settings.isPublicContacts())
                        return Mono.error(new ContactsClosedException("user contacts are closed"));

                    return userService.getUserContacts(
                            userId, PageRequest.of(pageNum, pageSize, Sort.Direction.valueOf(order.toUpperCase()), sortBy)
                    ).map(page -> page.map(User::getId));
                });
    }

    @GetMapping("/contacts")
    public Mono<Page<UUID>> personalContacts(@RequestHeader("userId") UUID authenticatedUser,
                                             @RequestParam(value = "size", defaultValue = "5") @Min(1) @Max(1000) Integer pageSize,
                                             @RequestParam(value = "page", defaultValue = "0") @PositiveOrZero Integer pageNum,
                                             @RequestParam(value = "orderBy", defaultValue = "DESC")
                                             @Pattern(
                                                     regexp = "asc|desc",
                                                     flags = {Pattern.Flag.CASE_INSENSITIVE}
                                             )
                                             String order,
                                             @RequestParam(name = "sortBy", defaultValue = "createdAt") String... sortBy
    ) {
        return userService.getUserContacts(
                authenticatedUser,
                PageRequest.of(pageNum, pageSize, Sort.Direction.valueOf(order.toUpperCase()), sortBy)
        ).map(page -> page.map(User::getId));
    }
}
