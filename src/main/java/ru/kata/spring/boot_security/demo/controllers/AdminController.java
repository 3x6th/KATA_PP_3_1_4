package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Objects;
import java.util.Scanner;

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
    public String adminPage(Model model) {
        List<User> allUsers = userService.listUsers();
        List<Role> allRoles = roleService.listRoles();
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("allRole", allRoles);
        return "admin/index";
    }

    @GetMapping(value = "/addNewUser")
    public String addNewUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", roleService.listRoles());
        return "admin/user-info";
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/updateInfo/{id}")
    public String updateUser(ModelMap model, @PathVariable int id, @ModelAttribute("user") User user) {
        model.addAttribute("user", userService.getUserByID(id));
        model.addAttribute("listRoles", roleService.listRoles());
        return "admin/user-info";
    }

    @PostMapping(value = "deleteUser/{id}")
    public String deleteUser(@PathVariable int id, ModelMap model) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
