package net.maslyna.security.service;

public interface BasicService {
    String extractDecodedBasic(String authHeader);

    String extractUsername(String decoded);

    String extractPassword(String decoded);

    String decodeBasic(String basic);
}
