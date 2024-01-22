package net.maslyna.security.entity;


import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN((short) 0), USER((short) 1);

    public final short value;

    @Nullable
    public static Role getRole(short source) {
        return switch (source) {
            case 0 -> ADMIN;
            case 1 -> USER;
            default -> null;
        };
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
