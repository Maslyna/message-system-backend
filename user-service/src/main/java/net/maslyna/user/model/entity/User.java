package net.maslyna.user.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
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
public class User {
    @Id
    @Column("user_id")
    private UUID id;

    @Column("email")
    private String email;

    @Column("username")
    private String username;

    @Column("bio")
    private String bio;

    @Column("status")
    private String status;

    @Column("last_login")
    private LocalDateTime lastLogin;

    @Column("created_at")
    private LocalDateTime createdAt;
}
