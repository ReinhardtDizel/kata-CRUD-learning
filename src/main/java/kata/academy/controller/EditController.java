package kata.academy.controller;

import kata.academy.model.Role;
import kata.academy.model.User;
import kata.academy.service.RoleService;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/editUser")
public class EditController {

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

    @GetMapping("/{id}")
    public String edit(Model model, @PathVariable Long id) {
        User user = userService.getById(id);
        List<Role> roles = roleService.getAll();
        model.addAttribute("user", user);
        model.addAttribute("possible_roles", roles);
        return "editUser";
    }

    @PostMapping
    public String edit(Model model, @ModelAttribute("userDto") User user) {
        userService.updateUser(user.getId(), user);
        return "redirect:/";
    }
}
