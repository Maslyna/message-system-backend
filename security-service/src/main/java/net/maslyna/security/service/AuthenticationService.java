package net.maslyna.security.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.exception.AccountNotFoundException;
import net.maslyna.security.exception.GlobalSecurityServiceException;
import net.maslyna.security.model.entity.Account;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final BasicService basicService;
    private final PasswordEncoder passwordEncoder;

    // Only for basic auth
    public Mono<String> login(final String basicAuthenticationHeader) { //TODO: add validation
        return Mono.fromCallable(() -> basicService.extractDecodedBasic(basicAuthenticationHeader))
                .map(decoded -> Tuples.of(basicService.extractUsername(decoded), basicService.extractPassword(decoded)))
                .flatMap(credentials -> login(credentials.getT1(), credentials.getT2()));
    }

    public Mono<String> login(final String username, final String password) {
        return accountService.getAccount(username)
                .flatMap(account -> {
                    if (!passwordEncoder.matches(password, account.getPassword())) {
                        return Mono.error(new GlobalSecurityServiceException(HttpStatus.UNAUTHORIZED, ""));
                    }
                    return Mono.just(jwtService.generateToken(account));
                })
                .onErrorResume(AccountNotFoundException.class, err -> Mono.error(new GlobalSecurityServiceException(HttpStatus.UNAUTHORIZED, "")));
    }

    public Mono<Account> validate(final String jwtAuthenticationHeader) {
        String token = jwtService.extractToken(jwtAuthenticationHeader);
        return Mono.just(token)
                .map(jwtService::isTokenValid)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new GlobalSecurityServiceException(HttpStatus.UNAUTHORIZED, "token not valid"));
                    } else {
                        return accountService.getAccount(jwtService.getUsername(token));
                    }
                });

    }
}
