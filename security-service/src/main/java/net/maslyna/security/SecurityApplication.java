package net.maslyna.security;

import lombok.extern.slf4j.Slf4j;
import net.maslyna.security.entity.Account;
import net.maslyna.security.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootApplication
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AccountRepository repository) {
        return (runner) -> {
            Flux.range(0, 100)
                    .map(i -> Account.builder()
                            .username("user " + i)
                            .password("password")
                            .build())
                    .map(repository::save)
                    .subscribe();
            repository.findAll()
                    .map(Account::toString)
                    .switchIfEmpty(Mono.just("NOT FOUND!"))
                    .subscribe(log::info);
        };
    }
}
