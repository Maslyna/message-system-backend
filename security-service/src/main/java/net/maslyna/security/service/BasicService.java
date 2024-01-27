package net.maslyna.security.service;

import java.util.Base64;

public interface BasicService {
    String extractDecodedBasic(String authHeader);

    String extractUsername(String decoded);

    String extractPassword(String decoded);

    String generateBasicAuthenticationHeader(String username, String password);

    default String decodeBasic(String basic) {
        if (basic != null) {
            return new String(Base64.getDecoder().decode(basic));
        }
        return null;
    }
}
