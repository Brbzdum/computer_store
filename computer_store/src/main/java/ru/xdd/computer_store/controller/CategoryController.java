package ru.xdd.computer_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xdd.computer_store.model.Category;
import ru.xdd.computer_store.service.CategoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Получение всех категорий
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();  // Возвращаем все категории
    }

    // Получение категории по ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.findCategoryById(id);  // Получаем Optional<Category>
        return category
                .map(ResponseEntity::ok)  // Если категория найдена, возвращаем 200 OK с категорией
                .orElseGet(() -> ResponseEntity.notFound().build());  // Если не найдена, возвращаем 404 Not Found
    }

    // Создание новой категории
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category newCategory = categoryService.createCategory(category);  // Создаём категорию
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);  // Возвращаем созданную категорию с статусом 201
    }

    // Удаление категории по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);  // Удаляем категорию
        return ResponseEntity.noContent().build();  // Возвращаем статус 204 No Content
    }
}
