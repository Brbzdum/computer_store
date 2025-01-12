package ru.xdd.computer_store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.*;
import ru.xdd.computer_store.repository.ProductRepository;
import ru.xdd.computer_store.repository.SaleRepository;
import ru.xdd.computer_store.repository.UserRepository;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    /**
     * Получить список покупок пользователя.
     */
    public List<Sale> getUserPurchases(User buyer) {
        return saleRepository.findByBuyer(buyer);
    }

    /**
     * Завершение продажи.
     *
     * @param productId ID товара
     * @param buyerEmail Email покупателя
     */
    @Transactional
    public void completeSale(Long productId, String buyerEmail) {
        // Найти товар
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден с ID: " + productId));

        // Найти покупателя
        User buyer = userRepository.findByEmail(buyerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Покупатель с email " + buyerEmail + " не найден"));

        // Создать запись о продаже
        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setBuyer(buyer); // Устанавливаем покупателя
        sale.setSalePrice(product.getPrice());
        sale.setManufacturer(product.getManufacturer());
        sale.setPurchasePrice(product.getPurchasePrice());
        sale.setSaleDate(LocalDateTime.now());

        // Сохранить запись о продаже
        saleRepository.save(sale);
    }

    public Map<Product, Long> getPopularProducts() {
        return saleRepository.findAll().stream()
                .collect(Collectors.groupingBy(Sale::getProduct, Collectors.counting()));
    }



    public Map<Manufacturer, Long> getManufacturerPopularity() {
        return saleRepository.findAll().stream()
                .collect(Collectors.groupingBy(sale -> sale.getProduct().getManufacturer(), Collectors.counting()));
    }

    public BigDecimal calculateRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        return saleRepository.findAll().stream()
                .filter(sale -> sale.getSaleDate().isAfter(startDate) && sale.getSaleDate().isBefore(endDate))
                .map(Sale::getSalePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    @Transactional
    public void createSale(Product product, User buyer, int quantity) {
        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setBuyer(buyer);
        sale.setManufacturer(product.getManufacturer());
        sale.setSalePrice(product.getPrice());
        sale.setPurchasePrice(product.getPurchasePrice());
        sale.setQuantity(quantity);

        saleRepository.save(sale);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    /**
     * Удаление продажи по ID.
     */
    @Transactional
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }

    public List<Sale> list() {
        return saleRepository.findAll();
    }

}
