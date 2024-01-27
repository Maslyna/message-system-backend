package net.maslyna.security.service;

import net.maslyna.security.model.entity.Account;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    // Only for basic auth
    Mono<String> login(String basicAuthenticationHeader);

    Mono<String> login(String username, String password);

    Mono<Account> validate(String jwtAuthenticationHeader);
}
