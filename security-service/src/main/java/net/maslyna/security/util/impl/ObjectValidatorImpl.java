package net.maslyna.security.util.impl;


import jakarta.validation.Validator;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import net.maslyna.security.exception.ObjectValidationException;
import net.maslyna.security.util.ObjectValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Map;

@Validated
@Component
@RequiredArgsConstructor
public class ObjectValidatorImpl implements ObjectValidator {

    private final Validator validator;

    @Override
    public <T> Mono<T> validate(T object) {
        var errors = validator.validate(object);
        if (errors.isEmpty()) {
            return Mono.just(object);
        }
        Map<String, Object> details = Map.of(
                "errors",
                errors.stream().map(err -> ErrorMessage.builder().message(err.getMessage())
                                .invalidValue(err.getInvalidValue()).build())
                        .toList()
        );
        return Mono.error(new ObjectValidationException(HttpStatus.BAD_REQUEST, details));
    }

    @Builder
    private static record ErrorMessage(
            String message,
            Object invalidValue
    ) {
    }
}
