package net.maslyna.security.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@RequiredArgsConstructor
@Service
public class BasicService {
    private static final String PREFIX = "Basic ";
    private final PasswordEncoder passwordEncoder;


    public String extractBasic(String authHeader) {
        if (authHeader != null && authHeader.startsWith(PREFIX)) {
            return decodeBasic(authHeader.substring(PREFIX.length()));
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

        return PREFIX + encodedCredentials;
    }

    private String decodeBasic(String basic) {
        if (basic != null) {
            return new String(Base64.getDecoder().decode(basic));
        }
        return null;
    }
}