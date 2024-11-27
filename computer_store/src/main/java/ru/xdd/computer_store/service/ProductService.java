package ru.xdd.computer_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Создание нового продукта
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Получение всех продуктов
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Поиск продукта по ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Поиск продукта по категории
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Обновление продукта
    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Продукт не найден"));
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());
        product.setManufacturer(updatedProduct.getManufacturer());
        product.setCategory(updatedProduct.getCategory());
        return productRepository.save(product);
    }

    // Удаление продукта
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
