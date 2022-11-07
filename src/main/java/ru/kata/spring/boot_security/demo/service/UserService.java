package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> listUsers();

    User userById(Long id);

    void save(User user);

    void delete(Long id);

    void create(User user);

    void makeAdmin(User user);
}
