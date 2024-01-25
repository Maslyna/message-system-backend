package net.maslyna.security.mapper;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.model.entity.Account;
import net.maslyna.security.model.response.AccountResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    public AccountResponse accountToAccountResponse(Account account) {
        if (account == null) {
            return null;
        }
        return AccountResponse.builder()
                .accountId(account.getId())
                .email(account.getUsername())
                .build();
    }
}
