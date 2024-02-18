package net.maslyna.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.user.mapper.UserMapper;
import net.maslyna.user.model.dto.UserDTO;
import net.maslyna.user.model.entity.User;
import net.maslyna.user.service.SettingService;
import net.maslyna.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class InfoController {
    private final UserMapper mapper;
    private final UserService userService;
    private final SettingService settingService;

    @GetMapping("/{userId}")
    public Mono<UserDTO> info(@PathVariable("userId") UUID userId,
                              @RequestHeader("userId") UUID authenticatedUser) {
        final Mono<User> user = userService.getUser(userId);
        if (userId.equals(authenticatedUser))
            return user.map(mapper::userToUserDto);

        return userService.isUserInContacts(userId, authenticatedUser)
                .flatMap(isInContacts -> {
                    if (!isInContacts)
                        return user.zipWith(settingService.getSettings(userId))
                                .map(t -> mapper.userToUserDtoWithPrivacySettings(t.getT1(), t.getT2()));

                    return user.map(mapper::userToUserDto);
                });
    }
}
