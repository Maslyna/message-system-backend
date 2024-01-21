package net.maslyna.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class AccountNotFoundException extends GlobalSecurityServiceException {
    public AccountNotFoundException(HttpStatusCode status) {
        super(status);
    }

    public AccountNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public AccountNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public AccountNotFoundException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
