package net.maslyna.security.service;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.entity.Account;
import net.maslyna.security.entity.Role;
import net.maslyna.security.exception.AccountAlreadyExistsException;
import net.maslyna.security.exception.AccountNotFoundException;
import net.maslyna.security.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.lang.StringTemplate.STR;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public Mono<Account> save(final String username, final String password) {
        return repository.existsByUsername(username)
                .flatMap(exists -> exists
                        ? Mono.error(new AccountAlreadyExistsException("account with username = '%s' already exists".formatted(username)))
                        : repository.save(createUser(username, password)));
    }

    public Mono<Account> getAccount(final UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("account with id = '%s' not found".formatted(id))));
    }

    public Mono<Account> getAccount(final String username) {
        return repository.findByUsername(username)
                .switchIfEmpty(Mono.error(
                        new AccountNotFoundException("account with username = '%s' not found".formatted(username))
                ));
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
