package net.maslyna.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class AccountAlreadyExistsException extends GlobalSecurityServiceException {
    public AccountAlreadyExistsException(HttpStatusCode status) {
        super(status);
    }

    public AccountAlreadyExistsException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }

    public AccountAlreadyExistsException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public AccountAlreadyExistsException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
