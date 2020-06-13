package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Role;
import com.zylex.carecooker.model.User;
import com.zylex.carecooker.repository.RoleRepository;
import com.zylex.carecooker.repository.UserRepository;
import com.zylex.carecooker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository,
                          UserService userService,
                          RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin/registration")
    public String getAdminRegistration(Model model) {
        model.addAttribute("admin", "");
        model.addAttribute("url", "/admin/registration");
        return "registration";
    }

    @PostMapping("/admin/registration")
    public String postAdminRegistration(User user, Model model) {
        if (!userService.saveAdmin(user)) {
            model.addAttribute("userExists", "");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/admin/user-list")
    public String getUserList(Model model) {
        List<User> users = userRepository.findAll();

        model.addAttribute("users", users);

        return "userEditList";
    }

    @GetMapping("/user/registration")
    public String getUserRegistration(Model model) {
        model.addAttribute("url", "/user/registration");
        return "registration";
    }

    @PostMapping("/user/registration")
    public String postUserRegistration(User user, Model model) {
        if (!userService.saveUser(user)) {
            model.addAttribute("userExists", "");
            return "registration";
        }

        return "redirect:/login";
    }
}