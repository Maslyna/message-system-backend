package net.maslyna.security.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.entity.Account;
import net.maslyna.security.enums.Role;
import net.maslyna.security.exception.AccountNotFoundException;
import net.maslyna.security.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final PasswordEncoder encoder;

    public Mono<Account> save(final String username, final String password) {
        return repository.save(createUser(username, password));
    }

    public Mono<Account> getAccount(final UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("user with id = {%s} not found".formatted(id))));
    }

    public Mono<Void> delete(final UUID id) {
        return repository.deleteById(id);
    }

    private Account createUser(final String username, final String password) {
        return Account.builder()
                .username(username)
                .password(encoder.encode(password))
                .role(Role.USER)
                .build();
    }
}
