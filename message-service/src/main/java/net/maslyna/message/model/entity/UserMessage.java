package net.maslyna.message.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Table("t_user_messages")
public class UserMessage {
    @PrimaryKeyColumn(name = "user_message_id", type = PrimaryKeyType.PARTITIONED, ordinal = 3)
    private UUID messageId;

    @PrimaryKeyColumn(name = "sender_id", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private UUID sender;

    @PrimaryKeyColumn(name = "receiver_id", type = PrimaryKeyType.PARTITIONED, ordinal = 2)
    private UUID receiver;

    @Column("content")
    private String content;

    @Column("file_ids")
    private List<UUID> files;

    @Column("viewed")
    private boolean viewed;

    @Column("deleted")
    private boolean deleted;

    @Column("edited")
    private boolean edited;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;
}
