package net.maslyna.security.config;

import lombok.extern.slf4j.Slf4j;
import net.maslyna.security.entity.RoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class DatabaseConfig {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(DatabaseClient client) {
        log.info("Apply R2DBC custom conversions");
        R2dbcDialect dialect = DialectResolver.getDialect(client.getConnectionFactory());
        List<Object> converters = new ArrayList<>(dialect.getConverters());

        converters.add(new RoleConverter.RoleReadingConverter());
        converters.add(new RoleConverter.RoleWritingConverter());

        return R2dbcCustomConversions.of(dialect, converters);
    }

}
