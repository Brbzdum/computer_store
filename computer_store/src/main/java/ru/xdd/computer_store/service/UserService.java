package ru.xdd.computer_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // Шифрование паролей

    // Создание нового пользователя
    @Transactional
    public User createUser(User user) {
        // Проверка на уникальность email и username
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Имя пользователя уже занято!");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email уже используется!");
        }

        // Шифрование пароля перед сохранением
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Получение всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Поиск пользователя по имени
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Удаление пользователя по ID
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        userRepository.delete(user);
    }

    // Редактирование пользователя
    @Transactional
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Обновляем поля
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());

        // Если пароль был изменён, то шифруем его
        if (!updatedUser.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(user);
    }
}
