package net.maslyna.security.service.impl;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.property.SecurityProperties;
import net.maslyna.security.service.BasicService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Base64;

@RequiredArgsConstructor
@Service
public class BasicServiceImpl implements BasicService {
    private final SecurityProperties properties;


    @Override
    public String extractDecodedBasic(String authHeader) {
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(properties.getBasicPrefix())) {
            return decodeBasic(authHeader.substring(properties.getBasicPrefix().length()));
        }
        return null;
    }

    @Override
    public String extractUsername(String decoded) {
        int separatorIndex = decoded.indexOf(':');
        return decoded.substring(0, separatorIndex);
    }

    @Override
    public String extractPassword(String decoded) {
        int separatorIndex = decoded.indexOf(':');
        return decoded.substring(separatorIndex + 1);
    }

    @Override public String generateBasicAuthenticationHeader(String username, String password) {
        String credentials = username + ":" + password;
        byte[] credentialsBytes = credentials.getBytes();
        String encodedCredentials = new String(Base64.getEncoder().encode(credentialsBytes));

        return properties.getBasicPrefix() + encodedCredentials;
    }

    }