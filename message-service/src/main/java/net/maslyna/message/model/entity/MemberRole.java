package net.maslyna.message.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@Setter

@Table("t_group_members_roles")
public class MemberRole {
    @PrimaryKey("group_id")
    private UUID groupId;

    @PrimaryKey("member_id")
    private UUID memberId;

    @Column("is_super")
    private UUID isSuper;

    @Column("role_name")
    private String name;

    @Column("role_description")
    private String description;
}
