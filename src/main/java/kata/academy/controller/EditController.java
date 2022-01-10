package kata.academy.controller;

import kata.academy.dto.UserDto;
import kata.academy.model.User;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/edit")
public class EditController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String edit(Model model, @PathVariable Long id) {
        User user = userService.getById(id);
        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        model.addAttribute("userDto", userDto);
        return "editUser";
    }

    @PostMapping
    public String edit(Model model, @ModelAttribute("userDto") UserDto userDto) {
        userService.updateUser(userDto);
        model.addAttribute("userDto", userDto);
        return "redirect:/";
    }
}
