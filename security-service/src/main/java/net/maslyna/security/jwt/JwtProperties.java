package net.maslyna.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String prefix;
    private String roleKey;
    private String secretKey;
    private Long tokenLiveTime;
}
