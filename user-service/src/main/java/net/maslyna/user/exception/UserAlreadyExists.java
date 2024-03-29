package net.maslyna.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class UserAlreadyExists extends GlobalUserServiceException {
    public UserAlreadyExists(HttpStatusCode status) {
        super(status);
    }

    public UserAlreadyExists(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }

    public UserAlreadyExists(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
