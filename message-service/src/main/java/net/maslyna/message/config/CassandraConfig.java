package net.maslyna.message.config;

import com.datastax.oss.driver.api.core.CqlSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public CqlSession cassandraMigrationCqlSession() {
        log.info("contact points = {}", contactPoints);
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
