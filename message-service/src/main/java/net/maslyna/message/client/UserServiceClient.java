package net.maslyna.message.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class UserServiceClient {
    private final WebClient client;

    public UserServiceClient(@Qualifier("userServiceWebClientBean") WebClient client) {
        this.client = client;
    }

    public Mono<Boolean> userExists(final UUID userId) {
        return client.get()
                .uri(builder -> builder
                        .path("/api/v1/users/{userId}/exists")
                        .build(userId))
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(Boolean.class);
                    }
                    return response.createError();
                });
    }
}
