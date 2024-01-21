package net.maslyna.security.exception;

import org.springframework.http.HttpStatusCode;

import java.util.Map;

public class ObjectValidationException extends GlobalSecurityServiceException {
    public ObjectValidationException(HttpStatusCode status) {
        super(status);
    }

    public ObjectValidationException(HttpStatusCode status, Map<String, Object> details) {
        super(status);
        this.details.putAll(details);
    }

    public ObjectValidationException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public ObjectValidationException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
