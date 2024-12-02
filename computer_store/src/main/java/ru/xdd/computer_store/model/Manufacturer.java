package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // Генерация стандартных методов
@Entity // Сущность для базы данных
@Table(name = "manufacturers") // Название таблицы
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автогенерация значения ID
    private Long id;

    @Column(nullable = false, unique = true, length = 100) // Уникальное обязательное поле
    private String name;
}
