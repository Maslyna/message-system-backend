package net.maslyna.security.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.property.SecurityProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@RequiredArgsConstructor
@Service
public class BasicService {
    private final SecurityProperties properties;
    private final PasswordEncoder passwordEncoder;


    public String extractDecodedBasic(String authHeader) {
        if (authHeader != null && authHeader.startsWith(properties.getBasicPrefix())) {
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

    public boolean isBasicAuthValid(String decoded, UserDetails userDetails) {
        String username = extractUsername(decoded);
        String password = extractPassword(decoded);

        return userDetails.getUsername().equals(username)
                && passwordEncoder.matches(password, userDetails.getPassword());
    }

    public String generateBasicAuth(String username, String password) {
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