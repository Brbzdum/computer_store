package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // Генерирует геттеры, сеттеры, toString(), equals(), hashCode()
@Entity // Сущность, соответствующая таблице в БД
@Table(name = "users") // Имя таблицы в БД
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автогенерация значения
    private Long id;

    @Column(nullable = false, unique = true, length = 50) // Уникальное обязательное поле
    private String username;

    @Column(nullable = false) // Обязательное поле
    private String password;

    @Column(nullable = false, unique = true, length = 100) // Уникальное обязательное поле
    private String email;

    @Enumerated(EnumType.STRING) // Сохранение enum как строкового значения
    @Column(nullable = false)
    private Role role;

    // Роли пользователя
    public enum Role {
        ADMIN, // Администратор
        CLIENT // Клиент
    }
}
