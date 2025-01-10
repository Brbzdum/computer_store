package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title; // Название товара

    @Column(nullable = false, length = 500)
    private String description; // Описание товара

    @Column(nullable = false)
    private BigDecimal price; // Цена товара

    @Column(name = "purchase_price", nullable = false)
    private BigDecimal purchasePrice; // Закупочная цена

    @Lob
    private byte[] mainImage; // Основное изображение (в формате байтов)

    @Lob
    private byte[] additionalImages; // Дополнительные изображения (JSON или массив байтов)

    @Column(nullable = false)
    private LocalDateTime createdAt; // Дата создания

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false) // Связь с таблицей производителей
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, unique = true, length = 50)
    private String sku; // Уникальный код товара

    @Column(nullable = false)
    private int stock; // Остатки товара

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
