package kata.academy.dao;

import kata.academy.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User findUserByUserName(String s);

    void updateUser(long id, User user);

    Optional<User> getById(long id);

    List<User> getAll();

    void saveUser(User user);

    void deleteUser(long id);
}
