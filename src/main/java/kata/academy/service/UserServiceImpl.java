package kata.academy.service;

import kata.academy.dao.UserDao;
import kata.academy.dto.UserDto;
import kata.academy.exception.UserAlreadyExist;
import kata.academy.model.Role;
import kata.academy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;

    private UserDao userDao;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void updateUser(UserDto user, List<Role> roles) {
        if (getUserByLogin(user.getLogin()) != null) {
            if (Objects.equals(getUserByLogin(user.getLogin()).getId(), user.getId())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userDao.updateUser(user, roles);
            } else {
                throw new UserAlreadyExist();
            }
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.updateUser(user, roles);
        }
    }

    @Override
    public User getById(long id) {
        return userDao.getById(id);
    }

    @Override
    public User getUserByLogin(String s) {
        return userDao.getUserByLogin(s);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    @Transactional
    public void saveUser(User user, List<Role> roles) {
        if (getUserByLogin(user.getLogin()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.saveUser(user);
            getUserByLogin(user.getLogin()).setRoles(new HashSet<>(roles));
        } else {
            throw new UserAlreadyExist();
        }
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }
}
