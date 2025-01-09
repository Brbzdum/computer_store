package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Проданный товар

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Пользователь, купивший товар

    @Column(nullable = false)
    private BigDecimal price; // Цена продажи

    @Column(nullable = false)
    private LocalDateTime saleDate; // Дата продажи
}
