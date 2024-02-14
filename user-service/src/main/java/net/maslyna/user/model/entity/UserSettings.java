package net.maslyna.user.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table("t_user_settings")
public class UserSettings {
    @Id
    @Column("user_id")
    private UUID id;

    @Column("is_public")
    private boolean isPublic;

    @Column("receive_emails")
    private boolean receiveEmails;

    @Column("receive_messages")
    private boolean receiveMessages;
}
