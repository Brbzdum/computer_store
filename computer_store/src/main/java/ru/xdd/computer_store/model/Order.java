package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Пользователь, оформивший заказ

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Товар в заказе

    @Column(nullable = false)
    private LocalDateTime orderDate; // Дата оформления заказа

    @Column(nullable = false)
    private BigDecimal totalPrice; // Общая стоимость заказа
}
