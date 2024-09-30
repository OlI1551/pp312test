package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findByUsername(String username);

    List<User> showAllUsers();

    User showUserById(Long id);

    void saveUser(User user);

    void updateUser(Long id, User user);

    void deleteUserById(Long id);
}
