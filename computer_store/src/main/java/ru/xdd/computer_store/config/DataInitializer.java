package ru.xdd.computer_store.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.model.enums.Role;
import ru.xdd.computer_store.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Проверяем, есть ли пользователи в базе
        if (userRepository.count() == 0) {
            // Создаём администратора
            User admin = new User();
            admin.setName("admin");
            admin.setEmail("admin@example.com");
            admin.setPhoneNumber("+7(977)777-77-77");
            admin.setPassword(passwordEncoder.encode("admin")); // Пароль зашифрован
            admin.setActive(true);
            admin.getRoles().add(Role.ROLE_ADMIN); // Добавляем роль администратора
            userRepository.save(admin);
            System.out.println("Администратор создан: admin / admin");
        }
    }
}
