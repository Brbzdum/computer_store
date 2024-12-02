package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xdd.computer_store.model.Product;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Поиск товаров по категории
    List<Product> findByCategoryId(Long categoryId);

    // Поиск товаров по производителю
    List<Product> findByManufacturerId(Long manufacturerId);

    // Поиск товара по названию
    List<Product> findByNameContainingIgnoreCase(String name);
}
