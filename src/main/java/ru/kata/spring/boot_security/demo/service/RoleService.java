package ru.kata.spring.boot_security.demo.service;

import java.util.List;

import ru.kata.spring.boot_security.demo.models.Role;

public interface RoleService {
    void save( Role role );
    List<Role> getAllRoles();
    List<Role> findRolesById( List<Long> rolesId );
}
