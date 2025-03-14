package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/homePage")
public class homePageController {
    
    private final RoleService roleService;

    @Autowired
    public homePageController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "index";
    }
}