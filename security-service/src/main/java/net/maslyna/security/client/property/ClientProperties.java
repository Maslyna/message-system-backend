package net.maslyna.security.client.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "client")
public record ClientProperties(
        String userServiceName
) {
    @ConstructorBinding
    public ClientProperties(String userServiceName) {
        this.userServiceName = userServiceName;
    }
}
