package net.maslyna.security.router;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.router.handler.AuthenticationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationRouterConfig {
    private final AuthenticationHandler handler;

    @Bean
    public RouterFunction<ServerResponse> authRoutes() {
        return RouterFunctions.route()
                .GET("/api/v1/login", authorizationExists(), handler::login)
                .GET("/api/v1/validate", authorizationExists(), handler::validate)
                .build();
    }

    private static RequestPredicate authorizationExists() {
        return request -> request.headers()
                .firstHeader(HttpHeaders.AUTHORIZATION) != null;
    }
}
