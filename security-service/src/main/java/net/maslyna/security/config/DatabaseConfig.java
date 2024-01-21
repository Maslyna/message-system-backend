package net.maslyna.security.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.codec.EnumCodec;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.security.entity.AccountReadingConverter;
import net.maslyna.security.entity.RoleWriteConverter;
import net.maslyna.security.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DatabaseConfig {
    private final Environment env;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host("localhost")
                        .port(5432)
                        .database("dev")
                        .username("sa")
                        .password("password")
                        .codecRegistrar(EnumCodec.builder().withEnum("role_t", Role.class).build())
                        .build()
        );
    }

    @Bean //DialectResolver.getDialect(connectionFactory)
    public R2dbcCustomConversions getCustomConverters() {
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, List.of(new RoleWriteConverter(), new AccountReadingConverter()));
    }
}
