package net.maslyna.user.repository;

import net.maslyna.user.model.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface UserRepository extends ReactiveMongoRepository<User, UUID> {
}
