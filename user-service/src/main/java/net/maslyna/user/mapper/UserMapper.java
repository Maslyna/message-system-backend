package net.maslyna.user.mapper;

import net.maslyna.user.model.dto.UserDTO;
import net.maslyna.user.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO userToUserDto(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public User userDtoToUser(UserDTO userDTO) {
        return User.builder()
                .email(userDTO.email())
                .username(userDTO.username())
                .build();
    }
}
