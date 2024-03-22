package net.maslyna.message.client;

import lombok.RequiredArgsConstructor;
import net.maslyna.message.exception.GlobalServiceException;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class UserServiceClient {
    private final WebClient.Builder client;
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;


    public Mono<Boolean> isUserExists() {
        return client.filter(lbFunction).build().get()
                .uri("http://user-service/api/v1/users/{userId}/exists")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().onStatus(HttpStatusCode::isError, response -> {
                    return response.bodyToMono(ProblemDetail.class)
                            .map(error -> new GlobalServiceException(HttpStatus.valueOf(error.getStatus()), error.getDetail()));
                })
                .bodyToMono(Boolean.class);
    }
}
