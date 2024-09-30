package ru.kata.spring.boot_security.demo.initiation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//ApplicationListener - интерфейс, который позволяет обрабатывать ApplicationEvent события
//ContextRefreshedEvent - публикуется автоматически после поднятия контекста
@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleDao roleDao;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInitializer(RoleDao roleDao, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleDao = roleDao;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //Создали роль юзер
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleDao.addRole(userRole);

        //Создали роль админ
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleDao.addRole(adminRole);

        List<Role> userRoles = List.of(userRole);
        List<Role> adminRoles = Arrays.asList(adminRole, userRole);

        User admin = new User();
        admin.setName("admin");
        admin.setSurName("admin");
        admin.setUsername("admin@mail.ru");
        admin.setPassword("admin");
        admin.setRoles(new HashSet<>(adminRoles));
        userService.saveUser(admin);

        User user = new User();
        user.setName("user");
        user.setSurName("user");
        user.setUsername("user@mail.ru");
        user.setPassword("user");
        user.setRoles(new HashSet<>(userRoles));
        userService.saveUser(user);

        User user2 = new User();
        user2.setName("user2");
        user2.setSurName("user2");
        user2.setUsername("user2@mail.ru");
        user2.setPassword("user2");
        user2.setRoles(new HashSet<>(userRoles));
        userService.saveUser(user2);

        User user3 = new User();
        user3.setName("user3");
        user3.setSurName("user3");
        user3.setUsername("user3@mail.ru");
        user3.setPassword("user3");
        user3.setRoles(new HashSet<>(userRoles));
        userService.saveUser(user3);
    }
}