package ru.xdd.computer_store.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.repository.ProductRepository;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
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
    public void saveProductWithImages(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) {
        // Логика сохранения продукта с изображениями
    }


    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private byte[] convertImagesToJson(List<byte[]> images) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsBytes(images);
    }
    public List<Product> filterProducts(Long categoryId, Long manufacturerId) {
        if (categoryId != null && manufacturerId != null) {
            return productRepository.findByCategoryIdAndManufacturerId(categoryId, manufacturerId);
        } else if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId);
        } else if (manufacturerId != null) {
            return productRepository.findByManufacturerId(manufacturerId);
        } else {
            return productRepository.findAll();
        }
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

