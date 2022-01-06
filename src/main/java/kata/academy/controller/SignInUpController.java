package kata.academy.controller;

import kata.academy.dto.Mapper;
import kata.academy.dto.UserDto;
import kata.academy.model.User;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class SignInUpController {

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

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("signup")
    public String sigUp(Model model) {
        model.addAttribute("user", new UserDto("user", "email", "password"));
        return "signup";
    }

    @PostMapping("signup")
    public ModelAndView sigUpRegister(@ModelAttribute("user") UserDto userDto) {
        User user = mapper.toSimpleUser(userDto);
        userService.saveUser(user);
        if (userService.findUserByUserName(user.getEmail()) != null) {
            return new ModelAndView(new RedirectView("login", true));
        }
        return new ModelAndView(new RedirectView("signup", true));
    }
}
