package net.maslyna.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class GroupNotFoundException extends GlobalServiceException {
    public GroupNotFoundException(HttpStatusCode status) {
        super(status);
    }

    public GroupNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public GroupNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
