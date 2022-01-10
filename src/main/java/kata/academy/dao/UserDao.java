package kata.academy.dao;

import kata.academy.dto.UserDto;
import kata.academy.model.User;

import java.util.List;

public interface UserDao {

    void updateUser(UserDto userDto);

    User getById(long id);

    List<User> getAll();

    void saveUser(User user);

    void deleteUser(long id);
}
