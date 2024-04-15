package net.maslyna.message.client;

import lombok.extern.slf4j.Slf4j;
import net.maslyna.message.client.property.ClientProperties;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class UserServiceClient {
    private final WebClient client;

    public UserServiceClient(WebClient.Builder builder,
                             ReactorLoadBalancerExchangeFilterFunction lbFunction,
                             ClientProperties properties) {
        this.client = builder.filter(lbFunction)
                .baseUrl(properties.userServiceBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, properties.baseMessageAgent())
                .build();;
    }

    public Mono<Map<UUID, Boolean>> usersExists(final Collection<UUID> users) { //TODO: Flux would be better choice, but I'm too LAZY
        return client.post()
                .uri("/api/ssc/v1/users/exists")
                .bodyValue(users)
                .exchangeToMono(response -> {
                    if (!response.statusCode().is2xxSuccessful())
                        return response.createError();
                    return response.bodyToMono(new ParameterizedTypeReference<>() {});
                });
    }

    public Mono<Boolean> isFriends(final UUID authenticated, final UUID userId) {
        return client.get()
                .uri("/api/ssc/v1/users/{userId}/permissions/addtogroup", userId)
                .header("userId", String.valueOf(authenticated))
                .exchangeToMono(response -> {
                    if (!response.statusCode().is2xxSuccessful())
                        return response.createError();
                    return response.bodyToMono(Boolean.class);
                });
    }
}
