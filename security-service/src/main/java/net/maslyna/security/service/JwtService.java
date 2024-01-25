package net.maslyna.security.service;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.security.property.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final SecurityProperties securityProperties;
    private final SecretKey secretKey;

    public String extractToken(final String authHeader) {
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(securityProperties.getJwtPrefix())) {
            return authHeader.substring(securityProperties.getJwtPrefix().length());
        }
        return null;
    }

    public String generateToken(Authentication authentication) {
        return generateToken(authentication.getName(), authentication.getAuthorities(), Map.of());
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername(), userDetails.getAuthorities(), Map.of());
    }

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities, Map<String, ?> extraClaims) {
        ClaimsBuilder claimsBuilder = Jwts.claims()
                .subject(username)
                .issuer("spring.webflux.test")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + securityProperties.getTokenLiveTime()))
                .add(extraClaims);
        if (!authorities.isEmpty()) {
            claimsBuilder.add(securityProperties.getRoleKey(), authorities.stream()
                    .map(GrantedAuthority::getAuthority).collect(joining(",")));
        }

        return Jwts.builder().claims(claimsBuilder.build())
                .signWith(secretKey, Jwts.SIG.HS256).compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public Jws<Claims> getClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    }


    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().verifyWith(this.secretKey)
                    .build().parseSignedClaims(token);
            // parseClaimsJws will check expiration date. No need do here.
            log.debug("expiration date: {}", claims.getPayload().getExpiration());
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Invalid JWT token: {}", e.getMessage());
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }
}