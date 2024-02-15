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

    @Column("is_public_email")
    private boolean isPublicEmail;

    @Column("is_public_status")
    private boolean isPublicStatus;

    @Column("is_public_bio")
    private boolean isPublicBio;

    @Column("is_public_last_login")
    private boolean isPublicLastLogin;

    @Column("is_public_contacts")
    private boolean isPublicContacts;

    @Column("receive_messages")
    private boolean receiveMessages;
}
