package net.maslyna.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;

public interface JwtService {
    String extractToken(String authHeader);

    String generateToken(Authentication authentication);

    String generateToken(UserDetails userDetails);

    String generateToken(String username, Collection<? extends GrantedAuthority> authorities, Map<String, ?> extraClaims);

    String getUsername(String token);

    Jws<Claims> getClaims(String token);

    boolean isTokenValid(String token);
}
