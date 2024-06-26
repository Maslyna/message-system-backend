package net.maslyna.security.client.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;


@ConfigurationProperties(prefix = "client")
public record ClientProperties(
        String userServiceBaseUrl,
        String baseSecurityAgent
) {
    @ConstructorBinding
    public ClientProperties {
    }
}
