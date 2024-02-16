package net.maslyna.user.mapper;

import net.maslyna.user.model.dto.RegistrationRequest;
import net.maslyna.user.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public RegistrationRequest userToUserDto(User user) {
        return RegistrationRequest.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public User userDtoToUser(RegistrationRequest registrationRequest) {
        return User.builder()
                .email(registrationRequest.email())
                .username(registrationRequest.username())
                .build();
    }
}
