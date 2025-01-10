package ru.xdd.computer_store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.xdd.computer_store.model.Category;
import ru.xdd.computer_store.service.CategoryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Список всех категорий.
     */
    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category-list"; // Шаблон для отображения списка категорий
    }

    /**
     * Страница добавления новой категории.
     */
    @GetMapping("/add")
    public String addCategoryPage(Model model) {
        model.addAttribute("category", new Category());
        return "category-add"; // Шаблон для добавления категории
    }

    /**
     * Добавление новой категории.
     */
    @PostMapping("/add")
    public String addCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    /**
     * Страница редактирования категории.
     */
    @GetMapping("/edit/{id}")
    public String editCategoryPage(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена с ID: " + id));
        model.addAttribute("category", category);
        return "category-edit"; // Шаблон для редактирования категории
    }

    /**
     * Обновление категории.
     */
    @PostMapping("/edit")
    public String editCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    /**
     * Удаление категории.
     */
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
