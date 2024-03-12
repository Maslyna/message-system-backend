package net.maslyna.message.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.cassandra")
@Data
public class CassandraProperties {
    private String keyspaceName;
    private List<String> contactPoints;
    private String username;
    private String password;
    private String localDatacenter;
}