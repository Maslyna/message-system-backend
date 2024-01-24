package net.maslyna.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class SecurityProperties {
    private String jwtPrefix;
    private String basicPrefix;
    private String roleKey;
    private String secretKey;
    private Long tokenLiveTime;
}
