package net.maslyna.message.client;

import lombok.extern.slf4j.Slf4j;
import net.maslyna.message.client.property.ClientProperties;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class UserServiceClient {
    private final WebClient client;
    private final ClientProperties properties;

    public UserServiceClient(WebClient.Builder builder,
                             ReactorLoadBalancerExchangeFilterFunction lbFunction,
                             ClientProperties properties) {
        this.client = builder.filter(lbFunction)
                .baseUrl(properties.userServiceBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, properties.baseMessageAgent())
                .build();
        this.properties = properties;
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

    public Flux<Tuple2<UUID, Boolean>> isUsersInContacts(final UUID userId, final Collection<UUID> users) {
        return client.post()
                .uri("/api/ssc/v1/users/checkContacts")
                .header(properties.userHeader(), String.valueOf(userId))
                .bodyValue(users)
                .exchangeToFlux(response -> {
                     if (!response.statusCode().is2xxSuccessful())
                         return Flux.from(response.createError());
                     return response.bodyToFlux(new ParameterizedTypeReference<>() {
                     });
                });
    }

    public Mono<Boolean> isContact(final UUID user, final UUID contact) {
        return client.get()
                .uri("/api/ssc/v1/users/{userId}/permissions/addtogroup", contact)
                .header(properties.userHeader(), String.valueOf(user))
                .exchangeToMono(response -> {
                    if (!response.statusCode().is2xxSuccessful())
                        return response.createError();
                    return response.bodyToMono(Boolean.class);
                });
    }
}
