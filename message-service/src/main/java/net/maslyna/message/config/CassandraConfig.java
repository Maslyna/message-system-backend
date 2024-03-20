package net.maslyna.message.config;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import lombok.RequiredArgsConstructor;
import net.maslyna.message.properties.CassandraProperties;
import org.cognitor.cassandra.migration.spring.CassandraMigrationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.DefaultCqlBeanNames;
import org.springframework.data.cassandra.config.EnableReactiveCassandraAuditing;

import java.net.InetSocketAddress;

@Configuration
@EnableReactiveCassandraAuditing
@RequiredArgsConstructor
public class CassandraConfig {
    private final CassandraProperties properties;

    @Bean(DefaultCqlBeanNames.SESSION)
    @Primary
    @DependsOn(CassandraMigrationAutoConfiguration.MIGRATION_TASK_BEAN_NAME)
    public CqlSession cassandraSession(CqlSessionBuilder sessionBuilder) {
        return sessionBuilder.build();
    }


    @Bean(CassandraMigrationAutoConfiguration.CQL_SESSION_BEAN_NAME)
    public CqlSession cassandraMigrationCqlSession() { // only for migrations
        return CqlSession.builder()
                .withKeyspace(properties.getKeyspaceName())
                .addContactPoints(
                        properties.getContactPoints().stream().map(str -> {
                            String[] address = str.split(":");
                            return InetSocketAddress.createUnresolved(address[0], Integer.parseInt(address[1]));
                        }).toList()
                )
                .withAuthCredentials(properties.getUsername(), properties.getPassword())
                .withLocalDatacenter(properties.getLocalDatacenter())
                .build();

    }
}
