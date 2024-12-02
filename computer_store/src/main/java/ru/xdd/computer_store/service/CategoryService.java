package ru.xdd.computer_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.Category;
import ru.xdd.computer_store.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Создание новой категории
    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Получение всех категорий
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    // Получение категории по ID
    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);  // Возвращаем Optional<Category>
    }

    // Удаление категории
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
