package kata.academy.controller;

import kata.academy.dto.UserDto;
import kata.academy.model.User;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/add")
public class AddController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String add(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "addUser";
    }

    @PostMapping
    public String save(@ModelAttribute("userDto") UserDto userDto) {
        User user = new User(userDto.getName(), userDto.getEmail());
        userService.saveUser(user);
        return "redirect:/";
    }

}
