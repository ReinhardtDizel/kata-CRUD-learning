package kata.academy.config.init;

import kata.academy.model.Role;
import kata.academy.model.User;
import kata.academy.service.UserService;

import java.util.Set;

public class TablesStartInitializer {

    private UserService userService;

    private String adminLogin;
    private String adminPassword;
    private String adminName;
    private String adminRole;
    private String userRole;

    public void setUserDao(UserService userService) {
        this.userService = userService;
    }

    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void init() {
        Role firstRole = new Role(adminRole);
        Role secondRole = new Role(userRole);
        User admin = new User(adminName, adminLogin, adminPassword, Set.of(firstRole, secondRole));
        if (userService.getUserByLogin(adminLogin) == null) {
            userService.saveUser(admin);
        }
    }
}
