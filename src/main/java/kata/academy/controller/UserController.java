package kata.academy.controller;

import kata.academy.dto.UserDto;
import kata.academy.model.User;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printWelcome(Model model) {
        List<User> users = new ArrayList<>(userService.getAll());
        model.addAttribute("users", users);
        return "index";
    }

    @RequestMapping(value = "/{request}={id}", method = RequestMethod.POST)
    public ModelAndView deleteUser(Model model,
                                   @PathVariable String request,
                                   @PathVariable Long id) {
        if (request.equals("delete")) {
            userService.deleteUser(id);
        } else if (request.equals("edit")) {
            return new ModelAndView(new RedirectView("editUser/" + id, true));
        }
        return new ModelAndView(new RedirectView("/", true));
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String printAddUser(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "addUser";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String saveUser(Model model,
                           @ModelAttribute("userDto") UserDto userDto) {
        User user = new User(userDto.getName(), userDto.getEmail());
        userService.saveUser(user);
        return "addUser";
    }

    @RequestMapping(value = "/editUser/{id}", method = RequestMethod.GET)
    public String printEditedUser(Model model,
                                  @PathVariable Long id) {
        User user = userService.getById(id);
        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        model.addAttribute("userDto", userDto);
        return "editUser";
    }

    @RequestMapping(value = "/editUser/{params}", method = RequestMethod.POST)
    public String editedUser(Model model,
                             @ModelAttribute("userDto") UserDto userDto) {
        System.out.println("================================================================");
        System.out.println(userDto.id + " " + userDto.name + " " + userDto.email);
        System.out.println("================================================================");
        userService.updateUser(userDto);
        model.addAttribute("userDto", userDto);
        return "editUser";
    }
}
