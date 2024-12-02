package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xdd.computer_store.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Найти категорию по имени
    Optional<Category> findByName(String name);
}
