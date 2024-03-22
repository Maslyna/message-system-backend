package net.maslyna.message.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class GlobalServiceException extends ResponseStatusException {
    public GlobalServiceException(HttpStatusCode status) {
        super(status);
    }

    public GlobalServiceException(HttpStatusCode status, String reason) {
        super(status, reason);
    }


}
