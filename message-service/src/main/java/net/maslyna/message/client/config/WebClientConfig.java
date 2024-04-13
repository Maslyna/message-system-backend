package net.maslyna.message.client.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
}