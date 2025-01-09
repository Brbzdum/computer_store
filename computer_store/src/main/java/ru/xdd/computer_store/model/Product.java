package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Производитель не может быть пустым")
    @Column(nullable = false, length = 100)
    private String brand; // Бренд товара

    @NotBlank(message = "Название товара не может быть пустым")
    @Size(max = 100, message = "Название товара не может превышать 100 символов")
    private String title; // Название товара

    @NotBlank(message = "Описание товара не может быть пустым")
    @Column(columnDefinition = "TEXT")
    private String description; // Описание товара

    @Column(nullable = false)
    private BigDecimal price; // Цена товара

    @Column(name = "purchase_price", nullable = false)
    private BigDecimal purchasePrice; // Закупочная цена товара

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // Категория товара

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer; // Производитель товара

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> images = new ArrayList<>(); // Список изображений

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Пользователь, добавивший товар

    private Long previewImageId; // ID главного изображения товара

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; // Дата создания товара

    public void addImageToProduct(Image image) {
        image.setProduct(this);
        images.add(image);
    }
}
