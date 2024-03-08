package net.maslyna.message;

import net.maslyna.message.model.entity.UserMessage;
import net.maslyna.message.repository.UserMessageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.UUID;

@SpringBootApplication
@EnableDiscoveryClient
public class MessageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserMessageRepository repository) {
        return (args) -> {
            Flux.range(0, 10)
                    .map(i -> UserMessage.builder()
                            .sender(UUID.randomUUID())
                            .receiver(UUID.randomUUID())
                            .content("TEST COMMENT")
                            .build())
                    .flatMap(repository::save)
                    .subscribe();
        };
    }
}
