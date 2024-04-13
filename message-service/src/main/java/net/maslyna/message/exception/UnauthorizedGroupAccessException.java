package net.maslyna.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class UnauthorizedGroupAccessException extends GlobalServiceException {
    public UnauthorizedGroupAccessException(HttpStatusCode status) {
        super(status);
    }

    public UnauthorizedGroupAccessException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }

    public UnauthorizedGroupAccessException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
