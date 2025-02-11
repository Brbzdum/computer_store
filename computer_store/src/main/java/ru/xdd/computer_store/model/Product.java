package ru.xdd.computer_store.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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



    @Column(name = "image_path") // Путь к файлу изображения
    private String imagePath;

    @Column(nullable = false)
    private LocalDateTime createdAt; // Дата создания

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false) // Связь с таблицей производителей
    private Manufacturer manufacturer;


    @Column(nullable = false)
    private int stock; // Остатки товара


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale> sales;


}
