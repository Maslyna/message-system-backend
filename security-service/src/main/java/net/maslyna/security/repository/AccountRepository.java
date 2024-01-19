package net.maslyna.security.repository;

import net.maslyna.security.entity.Account;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface AccountRepository extends R2dbcRepository<Account, UUID> {
}
