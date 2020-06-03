package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Role;
import com.zylex.carecooker.model.User;
import com.zylex.carecooker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "adminRegistration";
    }

    @PostMapping("/registration")
    public String postRegistration(User user, Model model) {
        User userFromDb = userRepository.findByUsernameIgnoreCase(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("adminExists", "");
            return "adminRegistration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ADMIN));
        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/list")
    public String getAdminList(Model model) {
        List<User> admins = userRepository.findAll();

        model.addAttribute("admins", admins);

        return "adminEditList";
    }
}