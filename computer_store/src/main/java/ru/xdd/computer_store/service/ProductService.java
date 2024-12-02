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
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // Получение продукта по ID
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);  // Возвращаем Optional<Product>
    }

    // Удаление продукта по ID
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
