package net.maslyna.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ContactsClosedException extends GlobalUserServiceException {

    public ContactsClosedException(String reason) {
        super(HttpStatus.FORBIDDEN, reason);
    }

    public ContactsClosedException(HttpStatusCode status) {
        super(status);
    }

    public ContactsClosedException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
