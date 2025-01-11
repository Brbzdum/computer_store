package ru.xdd.computer_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
        model.addAttribute("products", products);
        return "products/list"; // Шаблон для отображения списка товаров
    }


    /**
     * Просмотр информации о конкретном товаре.
     */
    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден с ID: " + id));
        model.addAttribute("product", product);
        return "products/view"; // Шаблон для отображения информации о товаре
    }



}
