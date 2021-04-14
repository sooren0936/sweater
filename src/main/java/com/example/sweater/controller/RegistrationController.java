package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
import java.util.Set;

@Controller
public class RegistrationController {

    private UserRepository userRepository;

    @Autowired
    public RegistrationController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(final User user,
                          final Map<String, Object> model) {
        final User dbUser = userRepository.findByUsername(user.getUsername());
        if (dbUser != null) {
            model.put("message", "User exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Set.of(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
