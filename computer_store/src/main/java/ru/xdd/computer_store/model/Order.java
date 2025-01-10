package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Пользователь, сделавший заказ

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Товар в заказе

    @Column(nullable = false)
    private LocalDateTime orderDate; // Дата заказа

    @Column(nullable = false)
    private String status; // Статус заказа (например, "Новый", "В обработке", "Завершен")
}
