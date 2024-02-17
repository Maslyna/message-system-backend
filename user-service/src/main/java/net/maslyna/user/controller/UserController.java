package net.maslyna.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.user.exception.ContactsClosedException;
import net.maslyna.user.mapper.UserMapper;
import net.maslyna.user.model.dto.RegistrationRequest;
import net.maslyna.user.model.dto.UserDTO;
import net.maslyna.user.model.entity.User;
import net.maslyna.user.service.UserPersistenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Mono<UserDTO> create(@Valid @RequestBody RegistrationRequest body, @RequestHeader("userId") UUID authenticatedUser) {
        return userPersistenceService.save(authenticatedUser, body.email(), body.username())
                .map(mapper::userToUserDto);
    }

    @DeleteMapping
    public Mono<Void> delete(@RequestHeader("userId") UUID authenticatedUser) {
        return userPersistenceService.delete(authenticatedUser);
    }

    @GetMapping("/{userId}")
    public Mono<UserDTO> info(@PathVariable("userId") UUID userId,
                              @RequestHeader("userId") UUID authenticatedUser) {
        final Mono<User> user = userPersistenceService.getUser(userId);
        if (userId.equals(authenticatedUser))
            return user.map(mapper::userToUserDto);

        return userPersistenceService.isUserInContacts(userId, authenticatedUser)
                .flatMap(isInContacts -> {
                    if (!isInContacts)
                        return user.zipWith(userPersistenceService.getSettings(userId))
                                .map(t -> mapper.userToUserDtoWithPrivacySettings(t.getT1(), t.getT2()));

                    return user.map(mapper::userToUserDto);
                });
    }

    @GetMapping("/{userId}/contacts")
    public Mono<Page<UUID>> contacts(@PathVariable("userId") UUID userId,
                                     @RequestHeader("userId") UUID authenticatedUser,
                                     @RequestParam(value = "size", defaultValue = "5") @Min(1) @Max(1000) Integer pageSize,
                                     @RequestParam(value = "page", defaultValue = "0") @PositiveOrZero Integer pageNum,
                                     @RequestParam(value = "orderBy", defaultValue = "DESC")
                                     @Pattern(
                                             regexp = "asc|desc",
                                             flags = {Pattern.Flag.CASE_INSENSITIVE},
                                             message = "error.validation.sort.direction.message"
                                     )
                                     String order,
                                     @RequestParam(name = "sortBy", defaultValue = "createdAt") String... sortBy
    ) {
        if (userId.equals(authenticatedUser))
            return userPersistenceService.getUserContacts(
                    userId,
                    PageRequest.of(pageNum, pageSize, Sort.Direction.valueOf(order.toUpperCase()), sortBy)
            ).map(page -> page.map(User::getId));

        return userPersistenceService.getSettings(userId)
                .flatMap(settings -> {
                    if (!settings.isPublicContacts())
                        return Mono.error(new ContactsClosedException("user contacts are closed"));

                    return userPersistenceService.getUserContacts(
                            userId, PageRequest.of(pageNum, pageSize, Sort.Direction.valueOf(order.toUpperCase()), sortBy)
                    ).map(page -> page.map(User::getId));
                });
    }
}
