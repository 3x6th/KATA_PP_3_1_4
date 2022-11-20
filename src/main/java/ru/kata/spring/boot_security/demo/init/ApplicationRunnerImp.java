package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ApplicationRunnerImp implements ApplicationRunner {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    public ApplicationRunnerImp(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> users = userService.listUsers();

        if (users.isEmpty()) {
            roleService.add(new Role("ROLE_ADMIN"));
            roleService.add(new Role("ROLE_USER"));
            Role admin = roleService.getRoleByID(1L);
            Role user = roleService.getRoleByID(2L);
            Set<Role> adminRole = new HashSet<>();
            Set<Role> userRole = new HashSet<>();
            adminRole.add(admin);
            userRole.add(user);
            userService.add(new User( "admin@mail.ru", passwordEncoder.encode("admin"), adminRole ));
            userService.add(new User( "user@mail.ru", passwordEncoder.encode("user"), userRole ));
        }
    }
}
