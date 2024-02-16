package net.maslyna.user.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class GlobalUserServiceException extends ResponseStatusException {
    public GlobalUserServiceException(HttpStatusCode status) {
        super(status);
    }

    public GlobalUserServiceException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
