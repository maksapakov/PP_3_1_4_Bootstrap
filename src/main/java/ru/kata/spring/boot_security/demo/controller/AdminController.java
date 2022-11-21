package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/edit")
    public String edit(User user, Model model) {
        model.addAttribute("users", userService.listUsers());
        return "edit_bootstrap";
    }

    @PostMapping("/edite")
    public String editUser(User user) {
        getUserRole(user);
        userService.update(user);
        return "redirect:/admin/edit";
    }

    @GetMapping("/edite/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edite";
    }

    @GetMapping("/delete")
    public String delete(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "delete_bootstrap";
    }

    @GetMapping("/start")
    public String index() {
        return "start_page";
    }

    @GetMapping
    public String users(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("messages", user);
        model.addAttribute("use", new User());
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("roleList", roleService.listRoles());
        return "users";
    }

    @GetMapping("/create")
    public String createUserForm(User user, Model model) {
        model.addAttribute("roleList", roleService.listRoles());
        return "create";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        getUserRole(user);
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleService.listRoles());
        return "update";
    }

    @PostMapping("update")
    public String updateUser(User user) {
        getUserRole(user);
        userService.update(user);
        return "redirect:/admin";
    }

    public void getUserRole(User user) {
        user.setRoles(user.getRoles().stream()
                .map(role -> roleService.findByNameRole(role.getRole()))
                .collect(Collectors.toSet()));
    }
}
