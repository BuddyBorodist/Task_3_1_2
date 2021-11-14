package ru.buddyborodist.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.buddyborodist.springboot.model.Role;
import ru.buddyborodist.springboot.model.User;
import ru.buddyborodist.springboot.service.RoleService;
import ru.buddyborodist.springboot.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/user")
    public String userInfo(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "user";
    }

    @GetMapping(value = "/admin")
    public String listUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin";
    }

    @GetMapping(value = "/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "/admin/new";
    }

    @PostMapping(value = "/admin/add-user")
    public String addUser(@ModelAttribute User user, @RequestParam(value = "selectedRoles") String[] selectedRoles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : selectedRoles) {
            roleSet.add(roleService.getRoleByName(role));
        }
        user.setRoles(roleSet);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit/{id}")
    public String editUserForm(@PathVariable("id") long id, Model model) {
        Optional<User> user = userService.getUserId(id);
        userService.getUserId(id).ifPresent(u -> model.addAttribute("user", u));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit";
    }

    @PatchMapping(value = "/edit/{id}")
    public String editUser(@ModelAttribute User user, @RequestParam(value = "selectedRoles") String[] selectedRoles) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : selectedRoles) {
            roleSet.add(roleService.getRoleByName(roles));
        }
        user.setRoles(roleSet);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping(value = "/remove/{id}")
    public String removeUser(@ModelAttribute User user ) {
        userService.deleteUserById(user.getId());
        return "redirect:/admin";
    }
}