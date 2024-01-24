package net.maslyna.security.router.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.exception.GlobalSecurityServiceException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HandlerService {

    public Mono<ServerResponse> createResponse(HttpStatusCode status, Object body) {
        return ServerResponse.status(status).bodyValue(body);
    }

    public Mono<ServerResponse> createResponse(HttpStatusCode status) {
        return ServerResponse.status(status).build();
    }

    public Mono<ServerResponse> createResponse(GlobalSecurityServiceException ex) {
        return createResponse(ex.getStatusCode(), ex.getBody());
    }
}
