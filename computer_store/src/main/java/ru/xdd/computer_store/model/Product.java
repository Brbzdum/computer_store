package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data // Генерация стандартных методов
@Entity // Сущность для базы данных
@Table(name = "products") // Название таблицы
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автогенерация значения ID
    private Long id;

    @Column(nullable = false, length = 100) // Уникальное и обязательное поле
    private String name;

    @Column(columnDefinition = "TEXT") // Поле для описания товара
    private String description;

    @Column(nullable = false) // Обязательное поле
    private BigDecimal price; // Используем BigDecimal для точных вычислений с деньгами

    @Column(nullable = false) // Обязательное поле
    private int stock; // Количество товара на складе

    @ManyToOne(fetch = FetchType.LAZY) // Ленивая загрузка данных производителя
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY) // Ленивая загрузка данных категории
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp // Автоматическое создание времени
    private LocalDateTime createdAt;
}
