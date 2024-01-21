package net.maslyna.security.router.handler;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.router.request.RegistrationRequest;
import net.maslyna.security.service.AccountService;
import net.maslyna.security.util.ObjectValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountHandler {
    private final AccountService service;
    private final ObjectValidator validator;

    public Mono<ServerResponse> createAccount(final ServerRequest request) {
        return request.bodyToMono(RegistrationRequest.class)
                .flatMap(validator::validate)
                .flatMap(req -> service.save(req.email(), req.password()))
                .flatMap(account -> createResponse(HttpStatus.CREATED, account));
    }

    private Mono<ServerResponse> createResponse(HttpStatusCode status, Object body) {
        return ServerResponse.status(status).bodyValue(body);
    }
}
