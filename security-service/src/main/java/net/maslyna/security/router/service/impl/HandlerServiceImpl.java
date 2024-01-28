package net.maslyna.security.router.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maslyna.security.exception.GlobalSecurityServiceException;
import net.maslyna.security.router.service.HandlerService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class HandlerServiceImpl implements HandlerService {

    @Override
    public Mono<ServerResponse> createResponse(HttpStatusCode status, Object body) {
        return ServerResponse.status(status).bodyValue(body);
    }

    @Override
    public Mono<ServerResponse> createResponse(HttpStatusCode status) {
        return ServerResponse.status(status).build();
    }

    @Override
    public Mono<ServerResponse> createResponse(GlobalSecurityServiceException ex) {
        return createResponse(ex.getStatusCode(), ex.getBody());
    }
}
