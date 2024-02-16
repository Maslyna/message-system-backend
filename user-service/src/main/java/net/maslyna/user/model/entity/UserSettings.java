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
    @Builder.Default
    private boolean isPublicEmail = true;

    @Column("is_public_status")
    @Builder.Default
    private boolean isPublicStatus = true;

    @Column("is_public_bio")
    @Builder.Default
    private boolean isPublicBio = true;

    @Column("is_public_last_login")
    @Builder.Default
    private boolean isPublicLastLogin = true;

    @Column("is_public_contacts")
    @Builder.Default
    private boolean isPublicContacts = true;

    @Column("receive_messages")
    @Builder.Default
    private boolean receiveMessages = true;
}
