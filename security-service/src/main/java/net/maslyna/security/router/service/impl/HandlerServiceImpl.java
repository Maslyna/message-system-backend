package net.maslyna.security.router.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.security.exception.GlobalSecurityServiceException;
import net.maslyna.security.router.service.HandlerService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class HandlerServiceImpl implements HandlerService {

    @Override
    public Mono<ServerResponse> createResponse(HttpStatusCode status, Object body) {
        return ServerResponse.status(status).contentType(MediaType.APPLICATION_JSON).bodyValue(body);
    }

    @Override
    public Mono<ServerResponse> createResponse(HttpStatusCode status) {
        return ServerResponse.status(status).contentType(MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Mono<ServerResponse> createErrorResponse(GlobalSecurityServiceException ex) {
        return createResponse(ex.getStatusCode(), ex.getBody());
    }

    @Override
    public Mono<ServerResponse> createErrorResponse(WebClientResponseException e) {
        return ServerResponse.status(e.getStatusCode()).contentType(MediaType.APPLICATION_JSON)
                .bodyValue(e.getResponseBodyAsString());
    }
}
