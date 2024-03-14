package net.maslyna.message.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
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
    @PrimaryKey("user_message_id")
    private UUID messageId;

    @PrimaryKey("sender_id")
    private UUID sender;

    @PrimaryKey("receiver_id")
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
