package net.maslyna.message.model.callback;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import net.maslyna.message.model.entity.UserMessage;
import org.reactivestreams.Publisher;
import org.springframework.data.cassandra.core.mapping.event.ReactiveBeforeConvertCallback;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class UserMessageBeforeConvertCallback implements ReactiveBeforeConvertCallback<UserMessage> {

    @Override
    public Publisher<UserMessage> onBeforeConvert(UserMessage entity, CqlIdentifier tableName) {
        if (entity.getMessageId() == null)
            entity.setMessageId(UUID.randomUUID());

        return Mono.just(entity);
    }
}
