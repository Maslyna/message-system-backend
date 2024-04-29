package net.maslyna.message.mapper;

import lombok.RequiredArgsConstructor;
import net.maslyna.message.model.dto.GroupDTO;
import net.maslyna.message.model.entity.Group;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupMapper {

    public GroupDTO toDto(Group group) {
        if (group == null) return null;

        return GroupDTO.builder()
                .groupId(group.getGroupId())
                .creator(group.getCreator())
                .name(group.getName())
                .isPublic(group.isPublic())
                .description(group.getDescription())
                .createdAt(group.getCreatedAt())
                .build();
    }
}
