package net.maslyna.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class NotSupportedAuthenticationMethodException extends GlobalSecurityServiceException {
    public NotSupportedAuthenticationMethodException(HttpStatusCode status) {
        super(status);
    }

    public NotSupportedAuthenticationMethodException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public NotSupportedAuthenticationMethodException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public NotSupportedAuthenticationMethodException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
