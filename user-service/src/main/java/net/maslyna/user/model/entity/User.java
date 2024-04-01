package net.maslyna.user.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table("t_users")
public class User implements Persistable<UUID> {

    @Id
    @Column("user_id")
    private UUID id;

    @Column("email")
    private String email;

    @Column("username")
    private String username;

    @Column("bio")
    @Builder.Default
    private String bio = "";

    @Column("status")
    @Builder.Default
    private String status = "";

    @Column("last_login")
    private LocalDateTime lastLogin;

    @Column("created_at")
    private LocalDateTime createdAt;


    @Transient
    @Builder.Default
    private boolean isNew = false;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
