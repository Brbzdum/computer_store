package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String brand; // Бренд товара (например, ASUS, HP)

    @Column(nullable = false, length = 100)
    private String title; // Название товара

    @Column(columnDefinition = "TEXT")
    private String description; // Описание товара

    @Column(nullable = false)
    private BigDecimal price; // Цена товара

    @Column(nullable = false)
    private BigDecimal magPrice; // Закупочная цена

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // Категория товара

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer; // Производитель товара

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> images = new ArrayList<>(); // Список изображений товара

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Пользователь, добавивший товар

    private Long previewImageId; // ID главного изображения

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; // Дата создания товара

    public void addImageToProduct(Image image) {
        image.setProduct(this);
        images.add(image);
    }
}
