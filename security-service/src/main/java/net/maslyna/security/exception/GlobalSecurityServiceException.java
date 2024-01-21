package net.maslyna.security.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

public class GlobalSecurityServiceException extends ResponseStatusException {
    public final Map<String, Object> details = new HashMap<>();

    public GlobalSecurityServiceException(HttpStatusCode status) {
        super(status);
    }

    public GlobalSecurityServiceException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public GlobalSecurityServiceException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
