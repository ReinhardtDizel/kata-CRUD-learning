package kata.academy.config.init;

import kata.academy.model.Role;
import kata.academy.model.User;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbInit {

    private final UserService userDao;

    private String adminPassword;
    private String adminUserName;
    private String adminRole;

    @Autowired
    public DbInit(UserService userDao) {
        this.userDao = userDao;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }

    public void init() {
        Role role = new Role(adminRole);
        User admin = new User(adminUserName, adminUserName, adminPassword, role);
        if (userDao.findUserByUserName(adminUserName) == null) {
            userDao.saveUser(admin);
        }
    }
}
