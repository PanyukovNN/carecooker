package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.User;
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
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserRepository userRepository,
                           UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "adminRegistration";
    }

    @PostMapping("/registration")
    public String postRegistration(User user, Model model) {
        if (!userService.saveAdmin(user)) {
            model.addAttribute("adminExists", "");
            return "adminRegistration";
        }

        return "redirect:/login";
    }

    @GetMapping("/list")
    public String getAdminList(Model model) {
        List<User> admins = userRepository.findAll();

        model.addAttribute("admins", admins);

        return "adminEditList";
    }
}