package ru.xdd.computer_store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.model.Sale;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.repository.ProductRepository;
import ru.xdd.computer_store.repository.SaleRepository;
import ru.xdd.computer_store.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

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
     * Получить список всех продаж.
     */
    public List<Sale> listAllSales() {
        return saleRepository.findAll();
    }

    /**
     * Получить список покупок пользователя.
     */
    public List<Sale> getUserPurchases(Principal principal) {
        return saleRepository.findByUserEmail(principal.getName());
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

        // Получить продавца
        User seller = product.getUser();

        // Создать запись о продаже
        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setUser(buyer); // Устанавливаем покупателя
        sale.setSeller(seller); // Устанавливаем продавца
        sale.setPrice(product.getPrice()); // Устанавливаем цену продажи
        sale.setPurchasePrice(product.getPurchasePrice()); // Устанавливаем закупочную цену
        sale.setSaleDate(LocalDateTime.now()); // Устанавливаем дату продажи

        // Сохранить запись о продаже
        saleRepository.save(sale);
    }

    /**
     * Удаление продажи по ID.
     */
    @Transactional
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}
