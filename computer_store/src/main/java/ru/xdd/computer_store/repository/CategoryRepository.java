package ru.xdd.computer_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xdd.computer_store.model.Category;

import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
