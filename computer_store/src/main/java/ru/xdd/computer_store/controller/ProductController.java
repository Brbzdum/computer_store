package ru.xdd.computer_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.xdd.computer_store.model.Category;
import ru.xdd.computer_store.model.Manufacturer;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.service.ProductService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Отображение списка товаров с возможностью фильтрации.
     */
    @GetMapping
    public String listProducts(@RequestParam(required = false) Long categoryId,
                               @RequestParam(required = false) Long manufacturerId,
                               Model model) {
        List<Product> products = productService.filterProducts(categoryId, manufacturerId);
        List<Category> categories = productService.getAllCategories();
        List<Manufacturer> manufacturers = productService.getAllManufacturers();

        model.addAttribute("content", "products/list");
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedManufacturerId", manufacturerId);
        return "products/list";
    }


    /**
     * Просмотр информации о конкретном товаре.
     */
    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден с ID: " + id));
        model.addAttribute("product", product);

        model.addAttribute("content", "products/view");

        return "products/view"; // Шаблон для отображения информации о товаре
    }



}
