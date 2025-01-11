package ru.xdd.computer_store.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.xdd.computer_store.model.Category;
import ru.xdd.computer_store.model.Manufacturer;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.repository.ProductRepository;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService, ManufacturerService manufacturerService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }


    public List<Product> getProductsByFilter(Long categoryId, Long manufacturerId) {
        log.debug("getProductsByFilter called with categoryId={} and manufacturerId={}", categoryId, manufacturerId);

        List<Product> products;

        if (categoryId == null && manufacturerId == null) {
            products = productRepository.findAll();
        } else if (categoryId != null && manufacturerId == null) {
            products = productRepository.findByCategoryId(categoryId);
        } else if (categoryId == null && manufacturerId != null) {
            products = productRepository.findByManufacturerId(manufacturerId);
        } else {
            products = productRepository.findByCategoryIdAndManufacturerId(categoryId, manufacturerId);
        }

        log.debug("Products found: {}", products.size());
        return products;
    }
    /**
     * Получение всех категорий (заглушка).
     */
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }


    @Transactional
    public void saveProductWithImages(Product product, MultipartFile mainImageFile, MultipartFile[] additionalImageFiles) throws IOException {
        if (!mainImageFile.isEmpty()) {
            product.setMainImage(mainImageFile.getBytes());
        }

        if (additionalImageFiles != null && additionalImageFiles.length > 0) {
            List<byte[]> additionalImages = new ArrayList<>();
            for (MultipartFile file : additionalImageFiles) {
                additionalImages.add(file.getBytes());
            }
            product.setAdditionalImages(convertImagesToJson(additionalImages));
        }

        productRepository.save(product);
    }

    @Transactional
    public void updateProductWithImages(Long id, Product updatedProduct, MultipartFile mainImageFile, MultipartFile[] additionalImageFiles) throws IOException {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));

        existingProduct.setTitle(updatedProduct.getTitle());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setPurchasePrice(updatedProduct.getPurchasePrice());

        if (!mainImageFile.isEmpty()) {
            existingProduct.setMainImage(mainImageFile.getBytes());
        }

        if (additionalImageFiles != null && additionalImageFiles.length > 0) {
            List<byte[]> additionalImages = new ArrayList<>();
            for (MultipartFile file : additionalImageFiles) {
                additionalImages.add(file.getBytes());
            }
            existingProduct.setAdditionalImages(convertImagesToJson(additionalImages));
        }

        productRepository.save(existingProduct);
    }



    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private byte[] convertImagesToJson(List<byte[]> images) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsBytes(images);
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

