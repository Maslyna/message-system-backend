package net.maslyna.message.client.config;

import net.maslyna.message.client.property.ClientProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder lbWebClient() {
        return WebClient.builder();
    }

    @Bean
    @Primary
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }

    @Bean(name = "userServiceWebClientBean")
    public WebClient userServiceWebClient(WebClient.Builder builder,
                                          ReactorLoadBalancerExchangeFilterFunction lbFunction,
                                          ClientProperties properties) {
        return builder.filter(lbFunction)
                .baseUrl(properties.userServiceBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, properties.baseMessageAgent())
                .build();
    }

}