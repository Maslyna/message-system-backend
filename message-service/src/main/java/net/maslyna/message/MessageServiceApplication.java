package net.maslyna.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveCassandraRepositories
@ConfigurationPropertiesScan
public class MessageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageServiceApplication.class, args);
    }

}
