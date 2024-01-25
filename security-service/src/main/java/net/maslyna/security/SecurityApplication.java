package net.maslyna.security;

import lombok.extern.slf4j.Slf4j;
import net.maslyna.security.model.entity.Account;
import net.maslyna.security.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AccountRepository repository) {
        return (args) -> {
            repository.findAll().map(Account::toString)
                    .subscribe(log::info);
        };
    }
}
