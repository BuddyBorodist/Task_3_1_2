package ru.buddyborodist.springboot.security;

import org.springframework.context.annotation.Configuration;
import ru.buddyborodist.springboot.model.Role;
import ru.buddyborodist.springboot.model.User;
import ru.buddyborodist.springboot.service.RoleService;
import ru.buddyborodist.springboot.service.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {
    private final RoleService roleService;

    private final UserService userService;


    public DataInitializer(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void createRoles(){
        Role roleAdmin = new Role();
        Role roleUser = new Role();
        roleAdmin.setName("ADMIN");
        roleUser.setName("USER");

        List<Role> roles = new ArrayList<>();
        roles.add(roleAdmin);
        roles.add(roleUser);
        roleService.saveRoles(roles);
    }

    @PostConstruct
    public void createUser(){
        Role roleAdmin = roleService.getRoleByName("ADMIN");
        Role roleUser = roleService.getRoleByName("USER");

        User user = new User();
        user.setRoles(Set.of(roleAdmin, roleUser));
        user.setUsername("admin");
        user.setPassword("123");
        userService.saveUser(user);
    }
}
