package net.maslyna.security.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "security")
public record SecurityProperties (
    String jwtPrefix,
    String basicPrefix,
    String roleKey,
    String secretKey,
    Long tokenLiveTime
) {
    @ConstructorBinding
    public SecurityProperties {

    }
}
