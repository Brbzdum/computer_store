package ru.xdd.computer_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.Category;
import ru.xdd.computer_store.repository.CategoryRepository;

import java.util.List;

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
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Поиск категории по ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Категория не найдена"));
    }

    // Удаление категории
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
