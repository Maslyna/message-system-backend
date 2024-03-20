package net.maslyna.message.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.message.repository.GroupMessageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupMessageRepository repository;
}
