package net.maslyna.security.util;

import reactor.core.publisher.Mono;

public interface ObjectValidator {
    <T> Mono<T> validate(T object);
}
