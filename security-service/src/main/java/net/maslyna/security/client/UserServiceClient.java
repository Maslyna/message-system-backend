package net.maslyna.security.client;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.client.exception.UserRegistrationException;
import net.maslyna.security.client.model.UserRegistrationDto;
import net.maslyna.security.client.property.ClientProperties;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class UserServiceClient {
    private final WebClient.Builder client;
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;
    private final ClientProperties properties;


    public Mono<Void> registration(final UserRegistrationDto request) {
        return client.filter(lbFunction).baseUrl(properties.userServiceBaseUrl()).build()
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
