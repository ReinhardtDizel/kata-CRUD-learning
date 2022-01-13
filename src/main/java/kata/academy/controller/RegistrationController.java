package kata.academy.controller;

import kata.academy.model.User;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class RegistrationController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String save(Model model) {

        return "signup";
    }

    @PostMapping
    public String save(@ModelAttribute("user") User user) {

        userService.saveUser(user);
        return "redirect:index";
    }
}
