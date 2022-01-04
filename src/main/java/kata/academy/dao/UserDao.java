package kata.academy.dao;

import kata.academy.dto.UserDto;
import kata.academy.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> updateUser(UserDto userDto);

    Optional<User> getById(long id);

    List<User> getAll();

    void saveUser(User user);

    void deleteUser(long id);
}
