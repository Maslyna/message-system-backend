package net.maslyna.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class R2dbcRepository {
    private final UserRepository repository;
    private final R2dbcEntityTemplate template;


}
