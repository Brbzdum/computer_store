package ru.xdd.computer_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.xdd.computer_store.model.Manufacturer;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.service.ProductService;

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
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        List<Manufacturer> manufacturers = productService.getAllManufacturers();

        model.addAttribute("content", "products/list.ftlh"); // Обновите путь, если нужно
        model.addAttribute("products", products);
        model.addAttribute("manufacturers", manufacturers);
        return "layout"; // Возвращайте базовый шаблон
    }





    /**
     * Просмотр информации о конкретном товаре.
     */
    /**
     * Просмотр информации о конкретном товаре.
     */
    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден с ID: " + id));

        model.addAttribute("product", product);
        model.addAttribute("content", "products/view.ftlh"); // Указываем путь к шаблону

        return "layout"; // Возвращаем базовый шаблон
    }




}
