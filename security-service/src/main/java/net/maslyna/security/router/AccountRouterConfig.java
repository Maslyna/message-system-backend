package net.maslyna.security.router;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.router.handler.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@RequiredArgsConstructor
public class AccountRouterConfig {
    private final AccountHandler handler;

    @Bean
    public RouterFunction<ServerResponse> accountRoutes() {
        return RouterFunctions.route()
                .POST("/api/v1/registration", accept(MediaType.APPLICATION_JSON), handler::createAccount)
                .GET("/api/v1/login", handler::login)
                .build();
    }
}
