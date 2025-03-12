package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

public interface RoleRepository extends JpaRepository<Role, Long> {
    User findByRoleName(String roleName);
}
