package ru.xdd.computer_store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.model.enums.Role;
import ru.xdd.computer_store.repository.UserRepository;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /**
     * Создание нового пользователя с отправкой email для активации.
     */
    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            return false; // Если пользователь с таким email уже существует
        }

        user.setActive(false); // Пользователь неактивен до активации
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Шифрование пароля
        user.getRoles().add(Role.ROLE_USER); // Роль по умолчанию - USER
        String activationCode = UUID.randomUUID().toString();
        user.setActivationCode(activationCode);

        userRepository.save(user); // Сохраняем пользователя в базе

        String activationLink = "http://localhost:8080/activate/" + activationCode;
        emailService.sendActivationEmail(email, activationLink); // Отправка email с кодом активации

        return true;
    }

    /**
     * Активация пользователя по коду.
     */
    @Transactional
    public boolean activateUser(String activationCode) {
        Optional<User> optionalUser = userRepository.findByActivationCode(activationCode);

        if (optionalUser.isEmpty()) {
            return false; // Код активации не найден
        }

        User user = optionalUser.get();
        user.setActive(true); // Активируем пользователя
        user.setActivationCode(null); // Очищаем код активации
        userRepository.save(user);

        return true;
    }

    /**
     * Получить список всех пользователей.
     */
    public List<User> list() {
        return userRepository.findAll();
    }

    /**
     * Заблокировать или разблокировать пользователя.
     */
    public void toggleUserBan(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setActive(!user.isActive()); // Меняем статус активности
            userRepository.save(user);
        }
    }

    /**
     * Изменить роли пользователя.
     */
    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values()) // Получаем список всех ролей
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear(); // Очищаем текущие роли

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key)); // Добавляем новые роли
            }
        }

        userRepository.save(user);
    }

    /**
     * Получить пользователя по Principal.
     */
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return new User(); // Если пользователь неавторизован
        }
        return userRepository.findByEmail(principal.getName()).orElse(new User());
    }

    /**
     * Найти пользователя по email.
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Проверить существование пользователя по email.
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Удалить пользователя по ID.
     */
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

}
