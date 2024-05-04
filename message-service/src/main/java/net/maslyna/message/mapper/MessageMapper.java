package net.maslyna.message.mapper;

import lombok.RequiredArgsConstructor;
import net.maslyna.message.model.dto.UserMessageDTO;
import net.maslyna.message.model.entity.UserMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    public UserMessageDTO toDTO(UserMessage message) {
        if (message == null)
            return null;

        return UserMessageDTO.builder()
                .messageId(message.getMessageId())
                .receiver(message.getReceiver())
                .sender(message.getSender())
                .content(message.getContent())
                .viewed(message.isViewed())
                .createdAt(message.getCreatedAt())
                .build();
    }

}
