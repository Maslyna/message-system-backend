package net.maslyna.security.router.handler;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.exception.GlobalSecurityServiceException;
import net.maslyna.security.mapper.AccountMapper;
import net.maslyna.security.property.SecurityProperties;
import net.maslyna.security.router.service.HandlerService;
import net.maslyna.security.service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationHandler {
    private final AuthenticationService authenticationService;
    private final AccountMapper mapper;
    private final HandlerService handlerService;
    private final SecurityProperties securityProperties;

    public Mono<ServerResponse> login(final ServerRequest request) {
        final String authHeader = request.headers().firstHeader(HttpHeaders.AUTHORIZATION);
        return authenticationService.login(authHeader)
                .flatMap(token -> ServerResponse.ok()
                        .header(HttpHeaders.AUTHORIZATION, securityProperties.jwtPrefix() + token)
                        .build())
                .onErrorResume(GlobalSecurityServiceException.class, handlerService::createErrorResponse);
    }

    public Mono<ServerResponse> validate(final ServerRequest request) {
        final String authHeader = request.headers().firstHeader(HttpHeaders.AUTHORIZATION);
        return authenticationService.validate(authHeader)
                .map(mapper::accountToAccountResponse)
                .flatMap(body -> handlerService.createResponse(HttpStatus.OK, body))
                .onErrorResume(GlobalSecurityServiceException.class, handlerService::createErrorResponse);
    }
}
