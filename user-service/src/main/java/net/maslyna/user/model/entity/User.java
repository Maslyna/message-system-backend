package net.maslyna.user.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document
public class User implements Serializable {
    @Id
    private UUID id;
    private String email;
    private String username;
    private String status;

    @CreatedDate
    private LocalDateTime createdAt;
}
