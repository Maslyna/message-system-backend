package net.maslyna.security.entity;

import io.r2dbc.spi.Row;
import net.maslyna.security.enums.Role;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

public class AccountReadingConverter implements Converter<Row, Account> {
    @Override
    public Account convert(Row source) {
        return Account.builder()
                .id(source.get("id", UUID.class))
                .username(source.get("username", String.class))
                .password(source.get("password", String.class))
                .role(source.get("role", Role.class))
                .accountNonExpired(source.get("account_non_expired", Boolean.class))
                .accountNonLocked(source.get("account_non_locked", Boolean.class))
                .credentialsNonExpired(source.get("credentials_non_expired", Boolean.class))
                .enabled(source.get("enabled", Boolean.class))
                .build();
    }
}
