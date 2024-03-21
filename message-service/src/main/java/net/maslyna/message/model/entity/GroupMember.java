package net.maslyna.message.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@Setter

@Table("t_group_members")
public class GroupMember {
    @PrimaryKeyColumn(name = "group_id", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private UUID groupId;

    @PrimaryKeyColumn(name = "member_id", type = PrimaryKeyType.PARTITIONED, ordinal = 2)
    private UUID memberId;

    @CreatedDate
    @Column("joined_at")
    private LocalDateTime joinedAt;
}
