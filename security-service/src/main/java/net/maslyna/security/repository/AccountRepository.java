package net.maslyna.security.repository;

import net.maslyna.security.model.entity.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccountRepository extends ReactiveCrudRepository<Account, UUID> {
    @Query("SELECT * FROM t_accounts WHERE username = :username")
    Mono<Account> findByUsername(String username);
    
    @Query("SELECT EXISTS(SELECT 1 FROM t_accounts WHERE username = :username)")
    Mono<Boolean> existsByUsername(String username);
}
