package net.maslyna.security.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.property.SecurityProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Base64;

@RequiredArgsConstructor
@Service
public class BasicService {
    private final SecurityProperties properties;


    public String extractDecodedBasic(String authHeader) {
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(properties.getBasicPrefix())) {
            return decodeBasic(authHeader.substring(properties.getBasicPrefix().length()));
        }
        return null;
    }

    public String extractUsername(String decoded) {
        int separatorIndex = decoded.indexOf(':');
        return decoded.substring(0, separatorIndex);
    }

    public String extractPassword(String decoded) {
        int separatorIndex = decoded.indexOf(':');
        return decoded.substring(separatorIndex + 1);
    }

    public String generateBasicAuthenticationHeader(String username, String password) {
        String credentials = username + ":" + password;
        byte[] credentialsBytes = credentials.getBytes();
        String encodedCredentials = new String(Base64.getEncoder().encode(credentialsBytes));

        return properties.getBasicPrefix() + encodedCredentials;
    }

    private String decodeBasic(String basic) {
        if (basic != null) {
            return new String(Base64.getDecoder().decode(basic));
        }
        return null;
    }
}