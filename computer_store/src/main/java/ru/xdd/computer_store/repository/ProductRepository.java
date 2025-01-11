package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.xdd.computer_store.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
        @EntityGraph(attributePaths = {"category", "manufacturer"})
        List<Product> findAll();

        @EntityGraph(attributePaths = {"category", "manufacturer"})
        List<Product> findByCategoryId(Long categoryId);

        @EntityGraph(attributePaths = {"category", "manufacturer"})
        List<Product> findByManufacturerId(Long manufacturerId);

        @EntityGraph(attributePaths = {"category", "manufacturer"})
        List<Product> findByCategoryIdAndManufacturerId(Long categoryId, Long manufacturerId);

        @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.manufacturer")
        List<Product> findAllWithDetails();
}


