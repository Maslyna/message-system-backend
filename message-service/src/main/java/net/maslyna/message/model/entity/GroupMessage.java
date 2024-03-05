package net.maslyna.message.model.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table("t_group_messages")
public class GroupMessage {
    @PrimaryKey
    @Column("group_message_id")
    private UUID messageId;

    @CreatedBy
    @Column("sender_id")
    private UUID sender;

    @Column("group_id")
    private UUID groupId;

    @Column("content")
    private String content;

    @Column("file_ids")
    private List<UUID> files;

    @Column("viewed_by_users")
    private List<UUID> viewedBy;

    @Column("deleted")
    private boolean deleted;

    @Column("edited")
    private boolean edited;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;
}
