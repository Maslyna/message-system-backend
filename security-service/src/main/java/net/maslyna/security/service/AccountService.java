package net.maslyna.security.service;

import net.maslyna.security.model.entity.Account;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccountService {
    Mono<Account> save(String username, String password);

    Mono<Account> getAccount(UUID id);

    Mono<Account> getAccount(String username);

    Mono<Void> delete(UUID id);
}
