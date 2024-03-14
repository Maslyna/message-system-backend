package net.maslyna.message.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.*;

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

    @Column("members_ids")
    @Builder.Default
    private Set<UUID> members = new HashSet<>();

    @Column("admins_ids")
    @Builder.Default
    private Set<UUID> admins = new HashSet<>();

    @Column("images_ids")
    private List<UUID> images = new ArrayList<>();

    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
