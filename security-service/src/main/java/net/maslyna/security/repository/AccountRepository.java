package net.maslyna.security.repository;

import net.maslyna.security.entity.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccountRepository extends ReactiveCrudRepository<Account, UUID> {
    Mono<Account> findByUsername(String username);
    Mono<Boolean> existsByUsername(String username);
}
