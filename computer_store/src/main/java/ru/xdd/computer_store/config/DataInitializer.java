package ru.xdd.computer_store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.xdd.computer_store.model.Role;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.service.RoleService;
import ru.xdd.computer_store.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // Создание ролей, если они не существуют
        try {
            roleService.findByName("ADMIN");
        } catch (Exception e) {
            roleService.save(new Role(null, "ADMIN"));
        }

        try {
            roleService.findByName("USER");
        } catch (Exception e) {
            roleService.save(new Role(null, "USER"));
        }

        // Создание администратора, если он не существует
        if(userService.findByUsername("admin").isEmpty()){
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin"); // Пароль будет зашифрован в сервисе
            admin.setEmail("admin@example.com");
            Set<String> roles = new HashSet<>();
            roles.add("ADMIN");
            userService.createUser(admin, roles);
            System.out.println("Администратор создан: admin / admin");
        }
    }
}
