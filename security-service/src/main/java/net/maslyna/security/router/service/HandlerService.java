package net.maslyna.security.router.service;

import net.maslyna.security.exception.GlobalSecurityServiceException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface HandlerService {
    Mono<ServerResponse> createResponse(HttpStatusCode status, Object body);

    Mono<ServerResponse> createResponse(HttpStatusCode status);

    Mono<ServerResponse> createErrorResponse(GlobalSecurityServiceException ex);

    Mono<ServerResponse> createErrorResponse(WebClientResponseException e);
}
