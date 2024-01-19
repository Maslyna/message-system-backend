package net.maslyna.security.entity;


import lombok.*;
import net.maslyna.security.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "t_accounts")
public class Account implements UserDetails {
    @Id
    private UUID id;
    private String username;
    private String password;

    private Role role;

    @Builder.Default
    private boolean accountNonExpired = false;
    @Builder.Default
    private boolean accountNonLocked = false;
    @Builder.Default
    private boolean credentialsNonExpired = false;
    @Builder.Default
    private boolean enabled = false;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(role);
    }
}
