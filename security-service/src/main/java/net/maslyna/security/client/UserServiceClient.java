package net.maslyna.security.client;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.client.property.ClientProperties;
import net.maslyna.security.model.request.RegistrationRequest;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class UserServiceClient {
    private final WebClient.Builder client;
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;
    private final ClientProperties properties;

}
