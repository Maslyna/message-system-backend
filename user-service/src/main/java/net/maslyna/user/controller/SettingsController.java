package net.maslyna.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.user.mapper.UserMapper;
import net.maslyna.user.model.dto.UserSettingsDTO;
import net.maslyna.user.service.SettingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/settings")
@RequiredArgsConstructor
@Validated
@Slf4j
public class SettingsController {
    private final SettingService service;
    private final UserMapper mapper;


    @GetMapping
    public Mono<UserSettingsDTO> settings(@RequestHeader("userId") UUID userId) {
        return service.getSettings(userId)
                .map(mapper::userSettingsToDto);
    }

    @PutMapping
    public Mono<UserSettingsDTO> update(@RequestHeader("userId") UUID userId, @RequestBody UserSettingsDTO dto) {
        return service.updateSettings(userId, dto)
                .map(mapper::userSettingsToDto);
    }
}
