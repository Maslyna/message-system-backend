package net.maslyna.security.service.impl;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.exception.AccountAlreadyExistsException;
import net.maslyna.security.exception.AccountNotFoundException;
import net.maslyna.security.model.entity.Account;
import net.maslyna.security.model.entity.Role;
import net.maslyna.security.repository.AccountRepository;
import net.maslyna.security.service.AccountService;
import net.maslyna.security.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    @Override
    public Mono<Account> save(final String username, final String password) {
        return repository.existsByUsername(username)
                .flatMap(exists -> exists
                        ? Mono.error(new AccountAlreadyExistsException("account with username = '%s' already exists".formatted(username)))
                        : repository.save(createUser(username, password)));
    }

    @Override
    public Mono<Account> getAccount(final UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("account with id = '%s' not found".formatted(id))));
    }

    @Override
    public Mono<Account> getAccount(final String username) {
        return repository.findByUsername(username)
                .switchIfEmpty(Mono.error(
                        new AccountNotFoundException("account with username = '%s' not found".formatted(username))
                ));
    }

    @Override public Mono<Void> delete(final UUID id) {
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
