package kata.academy.controller;

import kata.academy.model.Role;
import kata.academy.model.User;
import kata.academy.service.RoleService;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/addUser")
public class AddController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String add(Model model) {
        User user = new User();
        List<Role> roles = roleService.getAll();
        model.addAttribute("user", user);
        model.addAttribute("possible_roles", roles);
        return "addUser";
    }

    @PostMapping
    public String save(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/";
    }

}
