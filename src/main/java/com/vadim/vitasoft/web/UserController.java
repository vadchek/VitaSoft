package com.vadim.vitasoft.web;

import com.vadim.vitasoft.Role;
import com.vadim.vitasoft.User;
import com.vadim.vitasoft.UserService;
import com.vadim.vitasoft.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/selectedUser")
    public String selectedUser(@RequestParam String username, Model model) {
        User user = userService.loadUserByUsername(username);
        model.addAttribute("name", user.getUsername());
        model.addAttribute("role", user.getAuthorities());
        return "selectedUser";
    }

    @PostMapping("/selectedUser")
    public String addOperatorRole(@RequestParam String username) {
        User user = userService.loadUserByUsername(username);
        user.getRole().add(Role.OPERATOR);
        userRepository.save(user);
        return "redirect:/admin";
    }

}
