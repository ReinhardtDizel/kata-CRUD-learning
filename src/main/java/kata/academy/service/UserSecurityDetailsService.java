package kata.academy.service;

import kata.academy.dao.UserDao;
import kata.academy.model.User;
import kata.academy.model.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserSecurityDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(username);
        UserSecurity security = new UserSecurity(user);
        return security;
    }
}
