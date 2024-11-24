package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;


@Data // Генерирует геттеры, сеттеры, toString(), equals() и hashCode()
@Entity // Указывает, что класс является сущностью, связанной с таблицей в базе данных
@Table(name = "users") // Опционально, задает имя таблицы (если отличается от имени класса)
public class User {

    @Id // Указывает, что это поле — первичный ключ таблицы
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue автоматически генерирует значения для первичного ключа.
    // strategy = GenerationType.IDENTITY означает, что база данных будет сама увеличивать значение (auto_increment в MySQL).
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    // @Column связывает поле с конкретным столбцом в таблице.
    // nullable = false — поле не может быть NULL.
    // unique = true — значения в этом столбце должны быть уникальными.
    // length = 50 — максимальная длина строки.
    private String username;

    @Column(nullable = false)
    // nullable = false — это поле обязательно для заполнения (не может быть NULL).
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    // unique = true — для предотвращения повторяющихся email-адресов в базе.
    private String email;

    @Enumerated(EnumType.STRING)
    // @Enumerated используется для хранения enum-значений в базе данных.
    // EnumType.STRING — в базе будет храниться строковое значение (например, "ADMIN").
    @Column(nullable = false)
    private Role role;


    // Enum для ролей пользователя
    public enum Role {
        ADMIN, // Роль администратора
        CLIENT // Роль клиента
    }
}


