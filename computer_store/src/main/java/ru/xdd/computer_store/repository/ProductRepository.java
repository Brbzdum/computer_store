package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xdd.computer_store.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Найти все товары по категории
    List<Product> findByCategoryId(Long categoryId);

    // Найти все товары по производителю
    List<Product> findByManufacturerId(Long manufacturerId);

    // Найти товар по имени
    List<Product> findByNameContaining(String name);
}
