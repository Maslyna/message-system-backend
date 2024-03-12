package net.maslyna.message.model.callback;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import net.maslyna.message.model.entity.GroupMessage;
import org.reactivestreams.Publisher;
import org.springframework.data.cassandra.core.mapping.event.ReactiveBeforeConvertCallback;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class GroupMessageBeforeConvert implements ReactiveBeforeConvertCallback<GroupMessage> {

    @Override
    public Publisher<GroupMessage> onBeforeConvert(GroupMessage entity, CqlIdentifier tableName) {
        if (entity.getMessageId() == null)
            entity.setMessageId(UUID.randomUUID());

        return Mono.just(entity);
    }
}
