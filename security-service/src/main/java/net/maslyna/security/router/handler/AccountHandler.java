package net.maslyna.security.router.handler;

import lombok.RequiredArgsConstructor;
import net.maslyna.security.exception.GlobalSecurityServiceException;
import net.maslyna.security.model.request.RegistrationRequest;
import net.maslyna.security.router.service.HandlerService;
import net.maslyna.security.service.AccountService;
import net.maslyna.security.util.ObjectValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountHandler {
    private final AccountService accountService;
    private final HandlerService handlerService;
    private final ObjectValidator validator;

    public Mono<ServerResponse> createAccount(final ServerRequest request) {
        return request.bodyToMono(RegistrationRequest.class)
                .flatMap(validator::validate)
                .flatMap(req -> accountService.save(req.email(), req.password()))
                .flatMap(account -> handlerService.createResponse(HttpStatus.CREATED))
                .onErrorResume(WebClientResponseException.class, handlerService::createErrorResponse)
                .onErrorResume(GlobalSecurityServiceException.class, handlerService::createErrorResponse);
    }

    public Mono<ServerResponse> deleteAccount(final ServerRequest request) {
        Mono<UserDetails> user = request.principal().cast(UserDetails.class);

        return user.flatMap(u -> accountService.getAccount(u.getUsername()))
                .flatMap(account -> accountService.delete(account.getId()))
                .then(handlerService.createResponse(HttpStatus.OK))
                .onErrorResume(GlobalSecurityServiceException.class, handlerService::createErrorResponse);
    }
}
