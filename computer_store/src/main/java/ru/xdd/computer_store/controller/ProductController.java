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
     * Список всех товаров.
     */
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product-list"; // Страница с отображением списка товаров
    }

    /**
     * Просмотр информации о товаре.
     */
    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден с ID: " + id));
        model.addAttribute("product", product);
        return "product-view"; // Страница с деталями товара
    }

    /**
     * Отображение формы добавления нового товара.
     */
    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-add"; // Форма для добавления товара
    }

    /**
     * Обработка формы добавления товара.
     */
    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam("mainImage") MultipartFile mainImageFile,
                             @RequestParam("additionalImages") MultipartFile[] additionalImageFiles) {
        try {
            productService.saveProductWithImages(product, mainImageFile, additionalImageFiles);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения изображения", e);
        }
        return "redirect:/products";
    }

    /**
     * Отображение формы редактирования товара.
     */
    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден с ID: " + id));
        model.addAttribute("product", product);
        return "product-edit"; // Форма для редактирования товара
    }

    /**
     * Обработка формы редактирования товара.
     */
    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @ModelAttribute Product product,
                              @RequestParam("mainImage") MultipartFile mainImageFile,
                              @RequestParam("additionalImages") MultipartFile[] additionalImageFiles) {
        try {
            productService.updateProductWithImages(id, product, mainImageFile, additionalImageFiles);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка обновления изображения", e);
        }
        return "redirect:/products";
    }

    @GetMapping("/filter")
    public String filterProducts(@RequestParam(required = false) Long categoryId,
                                 @RequestParam(required = false) Long manufacturerId,
                                 Model model) {
        List<Product> products = productService.filterProducts(categoryId, manufacturerId);
        model.addAttribute("products", products);
        return "product-list";
    }


    /**
     * Удаление товара.
     */
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}

