package kata.academy.controller;

import kata.academy.dto.Mapper;
import kata.academy.dto.UserDto;
import kata.academy.model.User;
import kata.academy.model.UserPermissions;
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
import java.util.stream.Collectors;

@Controller
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

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printWelcome(Model model) {
        List<User> users = new ArrayList<>();
        userService.getAll().stream().filter(user -> user.getRoles().stream().noneMatch(
                        role -> role.getAuthority().equals(UserPermissions.ADMIN.getValue())))
                .forEach(users::add);
        model.addAttribute("users", users);
        return "index";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String printAdmin(Model model) {
        List<UserDto> users = userService.getAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "admin";
    }

    @RequestMapping(value = "/admin/{request}={id}", method = RequestMethod.POST)
    public ModelAndView deleteUser(Model model,
                                   @PathVariable String request,
                                   @PathVariable Long id) {
        if (request.equals("delete")) {
            userService.deleteUser(id);
        } else if (request.equals("edit")) {
            return new ModelAndView(new RedirectView("editUser/" + id, true));
        }
        return new ModelAndView(new RedirectView("/admin", true));
    }

    @RequestMapping(value = "/admin/addUser", method = RequestMethod.GET)
    public String printAddUser(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "addUser";
    }

    @RequestMapping(value = "/admin/addUser", method = RequestMethod.POST)
    public String saveUser(Model model,
                           @ModelAttribute("userDto") UserDto userDto) {
        //User user = new User(userDto.getName(), userDto.getEmail());
        //userService.saveUser(user);
        return "addUser";
    }

    @RequestMapping(value = "/admin/editUser/{id}", method = RequestMethod.GET)
    public String printEditedUser(Model model,
                                  @PathVariable Long id) {

        UserDto userDto = mapper.toDto(userService.getById(id));
        model.addAttribute("userDto", userDto);
        model.addAttribute("roles", userDto.roleString());
        return "editUser";
    }

    @RequestMapping(value = "/admin/editUser", method = RequestMethod.POST)
    public String editedUser(Model model,
                             @ModelAttribute("userDto") UserDto userDto) {
        //userService.updateUser(userDto.getId(),userDto);
        model.addAttribute("userDto", userDto);
        return "editUser";
    }
}
