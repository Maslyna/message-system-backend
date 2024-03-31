package net.maslyna.security.client.exception;

import net.maslyna.security.exception.GlobalSecurityServiceException;
import org.springframework.http.HttpStatusCode;

public class UserRegistrationException extends GlobalSecurityServiceException {
    public UserRegistrationException(HttpStatusCode status) {
        super(status);
    }

    public UserRegistrationException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public UserRegistrationException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
