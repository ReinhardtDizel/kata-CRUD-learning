package kata.academy.controller;

import kata.academy.dto.UserDto;
import kata.academy.model.Role;
import kata.academy.model.User;
import kata.academy.service.RoleService;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

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

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("possible_roles", roleService.getAll());
        return "createUser";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute("user") UserDto user) {
        Set<Role> roleSet = new HashSet<>(roleService.getRoleById(user.getRoles()));
        User createdUser = new User(user.getName(), user.getLogin(), user.getPassword(), roleSet);
        userService.saveUser(createdUser);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        User user = userService.getById(id);
        List<Role> roles = roleService.getAll();
        model.addAttribute("user", user);
        model.addAttribute("possible_roles", roles);
        return "editUser";
    }

    @PostMapping("/edit")
    public String edit(Model model, @ModelAttribute("userDto") User user) {
        userService.updateUser(user.getId(), user);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:index";
    }

}
