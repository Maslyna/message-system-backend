package net.maslyna.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class UserNotFoundException extends GlobalServiceException {
    public UserNotFoundException(HttpStatusCode status) {
        super(status);
    }

    public UserNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public UserNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
