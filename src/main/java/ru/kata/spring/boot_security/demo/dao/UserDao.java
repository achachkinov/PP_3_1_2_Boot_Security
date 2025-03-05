package ru.kata.spring.boot_security.demo.dao;

import java.util.List;

import ru.kata.spring.boot_security.demo.models.User;

public interface UserDao {
    void save(User user);
    User findById(Long id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteById(Long id);
}
