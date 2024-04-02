package net.maslyna.security.client;

import net.maslyna.security.client.model.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserServiceClient {
    private final WebClient client;

    public UserServiceClient(@Qualifier("userServiceWebClient") WebClient client) {
        this.client = client;
    }


    public Mono<Void> registration(final UserRegistrationDto request) {
        return client
                .post().uri("/api/v1/users")
                .bodyValue(request)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (!response.statusCode().is2xxSuccessful())
                        return response.createError();
                    return response.bodyToMono(Void.class);
                });
    }
}
