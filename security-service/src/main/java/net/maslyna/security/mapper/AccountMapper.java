package net.maslyna.security.mapper;

import net.maslyna.security.model.entity.Account;
import net.maslyna.security.model.response.AccountResponse;

public interface AccountMapper {
    AccountResponse accountToAccountResponse(Account account);
}
