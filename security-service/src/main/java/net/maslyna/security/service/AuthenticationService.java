package net.maslyna.security.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.exception.AccountNotFoundException;
import net.maslyna.security.exception.GlobalSecurityServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final BasicService basicService;

    public Mono<String> login(final String authenticationHeader) {
        String decoded = basicService.extractDecodedBasic(authenticationHeader);
        String username = basicService.extractUsername(decoded);
        return accountService.getAccount(username)
                .flatMap(account -> {
                    if (!basicService.isBasicAuthValid(decoded, account)) {
                        return Mono.error(new GlobalSecurityServiceException(HttpStatus.UNAUTHORIZED, ""));
                    }
                    return Mono.just(jwtService.generateToken(account));
                })
                .onErrorResume(AccountNotFoundException.class, (err) -> Mono.error(new GlobalSecurityServiceException(HttpStatus.UNAUTHORIZED, "")));
    }
}
