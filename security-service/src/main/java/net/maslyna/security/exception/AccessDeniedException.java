package net.maslyna.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class AccessDeniedException extends GlobalSecurityServiceException {
    public AccessDeniedException(HttpStatusCode status) {
        super(status);
    }

    public AccessDeniedException(String reason) {
        super(HttpStatus.valueOf(401));
    }

    public AccessDeniedException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public AccessDeniedException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
