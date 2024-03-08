package net.maslyna.message.model.callback;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import net.maslyna.message.model.entity.UserMessage;
import org.springframework.data.cassandra.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMessageBeforeConvertCallback implements BeforeConvertCallback<UserMessage> {

    @Override
    public UserMessage onBeforeConvert(UserMessage entity, CqlIdentifier tableName) {
        if (entity.getMessageId() == null)
            entity.setMessageId(UUID.randomUUID());

        return entity;
    }
}
