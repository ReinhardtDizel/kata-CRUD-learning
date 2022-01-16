package kata.academy.dao;

import kata.academy.dto.UserDto;
import kata.academy.model.Role;
import kata.academy.model.User;

import java.util.List;

public interface UserDao {

    User getUserByLogin(String s);

    void updateUser(UserDto user, List<Role> roles);

    User getById(long id);

    List<User> getAll();

    void saveUser(User user);

    void deleteUser(long id);
}
