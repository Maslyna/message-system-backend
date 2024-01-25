package net.maslyna.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.security.model.entity.converter.RoleReadingConverter;
import net.maslyna.security.model.entity.converter.RoleWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DatabaseConfig {

    @Bean //DialectResolver.getDialect(connectionFactory)
    public R2dbcCustomConversions getCustomConverters() {
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, List.of(new RoleWriteConverter(), new RoleReadingConverter()));
    }
}
