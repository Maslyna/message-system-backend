package net.maslyna.message.model.entity;

import lombok.*;
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

@Table("t_groups")
public class Group {
    @PrimaryKey("group_id")
    private UUID groupId;

    @Column("creator_id")
    private UUID creator;

    @Column("group_name")
    private String name;

    @Column("group_description")
    private String description;

    @Column("is_public")
    private boolean isPublic;

    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
