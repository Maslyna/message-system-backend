package net.maslyna.message.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Component
public class UserServiceClient {
    private final WebClient client;

    public UserServiceClient(@Qualifier("userServiceWebClientBean") WebClient client) {
        this.client = client;
    }

    public Mono<Map<UUID, Boolean>> userExists(final UUID userId) { //TODO: Flux would be better choice, but I'm too LAZY
        return client.get()
                .uri("/api/ssc/v1/users/exists")
                .exchangeToMono(response -> {
                    if (!response.statusCode().is2xxSuccessful())
                        return response.createError();
                    return response.bodyToMono(new ParameterizedTypeReference<>() {});
                });
    }
}
