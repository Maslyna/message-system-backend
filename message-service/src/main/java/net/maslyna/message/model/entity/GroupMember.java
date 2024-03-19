package net.maslyna.message.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@Setter

@Table("t_group_members")
public class GroupMember {
    @PrimaryKey("group_id")
    private UUID groupId;

    @PrimaryKey("member_id")
    private UUID memberId;

    @CreatedDate
    @Column("joined_at")
    private LocalDateTime joinedAt;
}
