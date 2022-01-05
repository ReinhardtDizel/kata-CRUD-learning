package kata.academy.dto;

import kata.academy.model.Role;
import kata.academy.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public UserDto toDto(User user) {

        Long id = user.getId();
        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();
        List<String> roles = user
                .getRoles()
                .stream()
                .map(Role::getAuthority)
                .collect(Collectors.toList());

        return new UserDto(id, name, email, password, roles);
    }

    public User toUser(UserDto userDto) {

        return new User(userDto.getName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRoles().stream().map(Role::new).collect(Collectors.toSet())
        );
    }
}
