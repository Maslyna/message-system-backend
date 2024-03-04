package net.maslyna.message.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Table("t_user_messages")
public class UserMessage {
    @PrimaryKey
    @Column("user_message_id")
    private UUID messageId;

    @CreatedBy
    @Column("sender_id")
    private UUID sender;

    @Column("receiver_id")
    private UUID receiver;

    @Column("content")
    private String content;

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
