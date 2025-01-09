package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xdd.computer_store.model.Product;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Найти все товары по категории
    List<Product> findByCategoryId(Long categoryId);

    // Найти все товары по производителю
    List<Product> findByManufacturerId(Long manufacturerId);

    // Найти товар по имени
    List<Product> findByNameContaining(String name);

    // Из kursovaya
    List<Product> findByTitle(String title);

    @Query("SELECT p FROM Product p WHERE (:searchWord IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :searchWord, '%'))) " +
            "AND (:categoryId IS NULL OR p.category.id = :categoryId)")
    List<Product> findByFilters(@Param("searchWord") String searchWord, @Param("categoryId") Long categoryId);
}
