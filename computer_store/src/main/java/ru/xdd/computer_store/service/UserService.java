package ru.xdd.computer_store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.User;
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
     * Создание нового пользователя.
     */
    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            return false;
        }

        user.setActive(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(User.Role.CLIENT); // Назначение роли по умолчанию
        String activationCode = UUID.randomUUID().toString();
        user.setActivationCode(activationCode);

        userRepository.save(user);

        String activationLink = "http://localhost:8080/activate/" + activationCode;
        emailService.sendActivationEmail(email, "Подтверждение регистрации", activationLink);

        return true;
    }

    /**
     * Активация пользователя по коду.
     */
    @Transactional
    public boolean activateUser(String activationCode) {
        Optional<User> optionalUser = userRepository.findByActivationCode(activationCode);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();
        user.setActive(true);
        user.setActivationCode(null);
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
            user.setActive(!user.isActive());
            userRepository.save(user);
        }
    }

    /**
     * Изменить роли пользователя.
     */
    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(User.Role.values())
                .map(Enum::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(User.Role.valueOf(key));
            }
        }

        userRepository.save(user);
    }

    /**
     * Получить пользователя по Principal.
     */
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName()).orElse(new User());
    }

    /**
     * Найти пользователя по username.
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Проверить существование пользователя по email.
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Проверить существование пользователя по username.
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Удалить пользователя по ID.
     */
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
