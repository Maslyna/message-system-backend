package net.maslyna.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.user.mapper.UserMapper;
import net.maslyna.user.model.dto.UserDTO;
import net.maslyna.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {
    private final UserMapper mapper;
    private final UserService userService;

    @PostMapping
    public Mono<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        return userService.save(userDTO)
                .map(mapper::userToUserDto);
    }
}
