package net.maslyna.security.repository;

import net.maslyna.security.entity.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface AccountRepository extends ReactiveCrudRepository<Account, UUID> {
}
