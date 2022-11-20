package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    UserService userService;
    RoleService roleService;
    PasswordEncoder passwordEncoder;


    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String adminPage(Model model, @AuthenticationPrincipal User user, @ModelAttribute("new_user") User new_user) {
        List<User> allUsers = userService.listUsers();
        List<Role> allRoles = roleService.listRoles();
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("user", user);
        model.addAttribute("new_user", new_user);
        return "admin/index";
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("new_user") User new_user) {
        List<User> allUsers = userService.listUsers();
        new_user.setPassword(passwordEncoder.encode(new_user.getPassword()));
        if (!allUsers.contains(new_user)) {
            userService.add(new_user);
        }
        return "redirect:/admin";
    }

    @PostMapping(value = "/edit/{id}")
    public String updateUser(ModelMap model, @PathVariable int id, @ModelAttribute("user") User user) {
        if (user.getPassword() == null) {
            userService.update(user);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.update(user);
        }
        return "redirect:/admin";
    }

    @PostMapping(value = "deleteUser/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
