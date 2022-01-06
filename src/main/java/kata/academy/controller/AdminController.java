package kata.academy.controller;

import kata.academy.dto.Mapper;
import kata.academy.dto.UserDto;
import kata.academy.model.User;
import kata.academy.model.UserPermissions;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

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
    public String printAdmin(Model model) {
        List<UserDto> users = userService.getAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/editUser/{id}")
    public String printEditedUser(Model model,
                                  @PathVariable Long id) {
        UserDto userDto = mapper.toDto(userService.getById(id));
        List<String> roles = Arrays.stream(UserPermissions.values())
                .filter(p -> !p.equals(UserPermissions.ADMIN))
                .map(UserPermissions::getValue)
                .collect(Collectors.toList());
        roles.removeIf(s -> userDto.getRoles().contains(s));
        model.addAttribute("userDto", userDto);
        model.addAttribute("permissions", userDto.getRoles());
        model.addAttribute("permissionsToAdd", roles);
        return "editUser";
    }

    @GetMapping("/addUser")
    public String printAddUser(Model model) {
        UserDto userDto = new UserDto();
        List<String> roles = Arrays.stream(UserPermissions.values()).filter(p -> !p.equals(UserPermissions.ADMIN)).map(UserPermissions::getValue).collect(Collectors.toList());
        model.addAttribute("userDto", userDto);
        model.addAttribute("permissions", roles);
        return "addUser";
    }

    @PostMapping("/{request}={id}")
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

    @PostMapping("/addUser")
    public ModelAndView saveUser(Model model,
                                 @ModelAttribute("userDto") UserDto userDto) {
        User user = mapper.toUser(userDto);
        userService.saveUser(user);
        if (userService.findUserByUserName(user.getEmail()) != null) {
            return new ModelAndView(new RedirectView("/admin", true));
        }
        return new ModelAndView(new RedirectView("/admin/addUser", true));
    }

    @PostMapping("/editUser")
    public String editedUser(Model model,
                             @ModelAttribute("userDto") UserDto userDto) {
        User user = mapper.toUser(userDto);
        userService.updateUser(userDto.getId(), user);
        return "editUser";
    }
}
