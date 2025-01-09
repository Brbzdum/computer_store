package ru.xdd.computer_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.service.ProductService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Получение всех продуктов
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    // Получение продукта по ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findProductById(id); // Получаем Optional<Product>
        return product
                .map(ResponseEntity::ok)  // Если продукт найден, возвращаем 200 OK с продуктом
                .orElseGet(() -> ResponseEntity.notFound().build());  // Если не найден, возвращаем 404 Not Found
    }

    // Создание нового продукта
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.createProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);  // Возвращаем созданный продукт с статусом 201
    }

    // Удаление продукта по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);  // Удаляем продукт
        return ResponseEntity.noContent().build();  // Возвращаем статус 204 No Content
    }
}
