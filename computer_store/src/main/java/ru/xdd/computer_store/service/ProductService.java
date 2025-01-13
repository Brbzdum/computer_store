package ru.xdd.computer_store.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.xdd.computer_store.model.Manufacturer;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.repository.ProductRepository;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ManufacturerService manufacturerService;


    public ProductService(ProductRepository productRepository, ManufacturerService manufacturerService) {
        this.productRepository = productRepository;
        this.manufacturerService = manufacturerService;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Value("${myapp.upload.dir}")
    private String uploadDir;

    /**
     * Получение всех категорий (заглушка).
     */
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }

    public List<Product> getProductsByManufacturerId(Long manufacturerId) {
        return productRepository.findByManufacturerId(manufacturerId);
    }

    @Transactional
    public void saveProductWithImage(Product product, MultipartFile mainImageFile) throws IOException {
        try {
            // Сохраняем продукт, чтобы он получил ID
            Product savedProduct = productRepository.save(product);

            if (mainImageFile != null && !mainImageFile.isEmpty()) {
                // productId точно не null, т.к. уже сохранён
                String imagePath = saveImageToFileSystem(mainImageFile, savedProduct.getId());
                savedProduct.setImagePath(imagePath);

                // Сохраняем ещё раз, чтоб обновить поле imagePath
                productRepository.save(savedProduct);
            }

            log.info("Транзакция выполнена успешно (saveProductWithImage).");
        } catch (Exception e) {
            log.error("Ошибка: транзакция откатывается.", e);
            throw e;
        }
    }

    private String saveImageToFileSystem(MultipartFile file, Long productId) throws IOException {
        File directory = new File(uploadDir); // что-то вроде "uploads/images/products"
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        // Имя файла: product_17_timestamp.jpg
        String fileName = "product_" + productId + "_" + System.currentTimeMillis() + "." + extension;
        File destination = new File(directory, fileName);

        file.transferTo(destination);

        // Вернём относительный путь
        return "/uploads/images/products/" + fileName;
    }


    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "jpg"; // Default extension
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    @Transactional
    public void updateProductWithImage(Product product, MultipartFile mainImageFile) throws IOException {
        try {
            if (mainImageFile != null && !mainImageFile.isEmpty()) {
                String imagePath = saveImageToFileSystem(mainImageFile, product.getId());
                product.setImagePath(imagePath);
            }
            productRepository.save(product);
            log.info("Транзакция обновления выполнена успешно.");
        } catch (Exception e) {
            log.error("Ошибка при обновлении продукта: ", e);
            throw e;
        }
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден с ID: " + productId));
        int updatedStock = product.getStock() - quantity;
        if (updatedStock < 0) {
            throw new IllegalStateException("Недостаточно товара на складе");
        }
        product.setStock(updatedStock);
        productRepository.save(product);
    }


}

