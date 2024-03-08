package net.maslyna.message.config;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import lombok.extern.slf4j.Slf4j;
import org.cognitor.cassandra.migration.spring.CassandraMigrationAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.DefaultCqlBeanNames;
import org.springframework.data.cassandra.config.EnableCassandraAuditing;

import java.net.InetSocketAddress;
import java.util.List;

@Configuration
@Slf4j
@EnableCassandraAuditing
public class CassandraConfig { //TODO: extract this into CassandraProperties

    @Value("${spring.cassandra.keyspace-name}")
    private String keyspace;

    @Value("${spring.cassandra.contact-points}")
    private List<String> contactPoints;

    @Value("${spring.cassandra.username}")
    private String username;

    @Value("${spring.cassandra.password}")
    private String password;

    @Value("${spring.cassandra.local-datacenter}")
    private String localDatacenter;

    //
    @Bean(DefaultCqlBeanNames.SESSION)
    @Primary
// ensure that the keyspace is created if needed before initializing spring-data session
    @DependsOn(CassandraMigrationAutoConfiguration.MIGRATION_TASK_BEAN_NAME)
    public CqlSession cassandraSession(CqlSessionBuilder sessionBuilder) {
        return sessionBuilder.build();
    }


    @Bean(CassandraMigrationAutoConfiguration.CQL_SESSION_BEAN_NAME)
    public CqlSession cassandraMigrationCqlSession() {
        return CqlSession.builder()
                .withKeyspace(keyspace)
                .addContactPoints(
                        contactPoints.stream().map(str -> {
                            String[] address = str.split(":");
                            return InetSocketAddress.createUnresolved(address[0], Integer.parseInt(address[1]));
                        }).toList()
                )
                .withAuthCredentials(username, password)
                .withLocalDatacenter(localDatacenter)
                .build();

    }
}
