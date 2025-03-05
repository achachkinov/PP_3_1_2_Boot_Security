package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class UserController {
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("newUser") User user) {
        userService.save(user);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("updateUser") User user) {
        userService.updateUser(user);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteById(id);
        return "redirect:/";
    }
}