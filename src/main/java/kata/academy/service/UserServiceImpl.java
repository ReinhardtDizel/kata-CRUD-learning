package kata.academy.service;

import kata.academy.dao.UserDao;
import kata.academy.dto.UserDto;
import kata.academy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        userDao.updateUser(userDto);
    }

    @Override
    public User getById(long id) {
        Optional<User> optionalUser = userDao.getById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public User findUserByUserName(String s) {
        return userDao.findUserByUserName(s);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }
}
