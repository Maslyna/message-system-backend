package net.maslyna.security.router.service;

import net.maslyna.security.exception.GlobalSecurityServiceException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface HandlerService {
    Mono<ServerResponse> createResponse(HttpStatusCode status, Object body);

    Mono<ServerResponse> createResponse(HttpStatusCode status);

    Mono<ServerResponse> createResponse(GlobalSecurityServiceException ex);
}
