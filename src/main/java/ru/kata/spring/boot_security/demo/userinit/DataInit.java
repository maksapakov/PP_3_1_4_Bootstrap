package ru.kata.spring.boot_security.demo.userinit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInit {

    @Autowired
    private RoleServiceImpl roleService;
    private final UserServiceImpl userService;

    public DataInit(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");

        roleService.addRole(role1);
        roleService.addRole(role2);

        Set<Role> roleAdmin = new HashSet<>();
        Set<Role> roleUser = new HashSet<>();

        roleAdmin.add(role1);
        roleUser.add(role2);

        User user1 = new User("admin", "admin", "Moscow", "112", "admin@gmail.com", roleAdmin);
        User user2 = new User("user", "user",  "Congo","911", "user@gmail.com", roleUser);

        userService.add(user1);
        userService.add(user2);
    }
}
