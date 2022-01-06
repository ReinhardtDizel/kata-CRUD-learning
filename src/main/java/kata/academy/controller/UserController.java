package kata.academy.controller;

import kata.academy.dto.Mapper;
import kata.academy.model.User;
import kata.academy.model.UserPermissions;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private UserService userService;
    private Mapper mapper;

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String printWelcome(Model model) {
        List<User> users = new ArrayList<>();
        userService.getAll().stream().filter(user -> user.getRoles().stream().noneMatch(
                        role -> role.getAuthority().equals(UserPermissions.ADMIN.getValue())))
                .forEach(users::add);
        model.addAttribute("users", users);
        return "index";
    }
}
