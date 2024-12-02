package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data // Генерация стандартных методов
@Entity // Сущность для базы данных
@Table(name = "order_details") // Название таблицы
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автогенерация значения ID
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Ленивая загрузка данных заказа
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY) // Ленивая загрузка данных товара
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false) // Обязательное поле
    private int quantity; // Количество товара в заказе

    @Column(nullable = false) // Обязательное поле
    private BigDecimal price; // Цена товара в момент заказа
}
