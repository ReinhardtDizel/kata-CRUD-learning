package kata.academy.service;

import kata.academy.model.User;

import java.util.List;

public interface UserService {

    void updateUser(long id, User user);

    User getById(long id);

    User getUserByLogin(String s);

    List<User> getAll();

    void saveUser(User user);

    void deleteUser(long id);
}
