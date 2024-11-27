package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data // Генерация стандартных методов
@Entity // Сущность для базы данных
@Table(name = "orders") // Название таблицы
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автогенерация значения ID
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Ленивая загрузка данных пользователя
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime orderDate; // Дата оформления заказа

    @Column(nullable = false)
    private BigDecimal totalPrice; // Общая стоимость заказа

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) // Связь с деталями заказа
    private List<OrderDetail> orderDetails;
}
