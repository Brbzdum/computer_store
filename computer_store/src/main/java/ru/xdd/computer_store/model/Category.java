package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name; // Название категории (например, "Ноутбуки")

    @Column(length = 255)
    private String description; // Описание категории
}
