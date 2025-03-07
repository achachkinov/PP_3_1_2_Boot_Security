package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class createDefaultRoleAndUsers implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public createDefaultRoleAndUsers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        if (userService.getAllUsers().isEmpty()) {
            System.out.println("Adding initial users...");

            Role adminRole = new Role("ROLE_ADMIN");
            Role userRole = new Role("ROLE_USER");

            roleService.save(adminRole);
            roleService.save(userRole);

            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setAge("20");
            admin.setUsername("admin");
            admin.setPassword("admin");
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            admin.setRoles(adminRoles);

            User user = new User();
            user.setFirstName("User");
            user.setLastName("User");
            user.setAge("20");
            user.setUsername("user");
            user.setPassword("user");
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            user.setRoles(userRoles);

            userService.save(admin);
            userService.save(user);

            System.out.println("created default user and admin");
        } else {
            System.out.println("default user and admin not created");
        }
    }
}