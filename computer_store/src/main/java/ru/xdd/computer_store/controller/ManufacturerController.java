package ru.xdd.computer_store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.xdd.computer_store.model.Manufacturer;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.service.ManufacturerService;
import ru.xdd.computer_store.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;
    private final ProductService productService;

    /**
     * Список всех производителей.
     */
    @GetMapping
    public String getAllManufacturers(Model model) {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        model.addAttribute("manufacturers", manufacturers);
        return "manufacturer-list"; // Шаблон для отображения списка производителей
    }

    /**
     * Просмотр информации о производителе и его продуктах с фильтрацией по категориям.
     */
    @GetMapping("/{id}")
    public String viewManufacturer(@PathVariable Long id,
                                   @RequestParam(required = false) Long categoryId,
                                   Model model) {
        Manufacturer manufacturer = manufacturerService.getManufacturerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Производитель не найден с ID: " + id));

        // Используем метод filterProducts вместо нового метода
        List<Product> products = productService.filterProducts(categoryId, id);
        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("products", products);
        model.addAttribute("categories", productService.getAllCategories()); // Для фильтрации
        return "manufacturer-view"; // Шаблон для отображения страницы производителя
    }
}

