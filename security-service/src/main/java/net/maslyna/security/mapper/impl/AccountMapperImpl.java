package net.maslyna.security.mapper.impl;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.mapper.AccountMapper;
import net.maslyna.security.model.entity.Account;
import net.maslyna.security.model.response.AccountResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {

    @Override public AccountResponse accountToAccountResponse(Account account) {
        if (account == null) {
            return null;
        }
        return AccountResponse.builder()
                .accountId(account.getId())
                .email(account.getUsername())
                .build();
    }
}
