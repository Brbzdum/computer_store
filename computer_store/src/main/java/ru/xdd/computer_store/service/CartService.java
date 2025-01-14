package ru.xdd.computer_store.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.xdd.computer_store.model.*;
import ru.xdd.computer_store.repository.CartRepository;
import ru.xdd.computer_store.repository.ProductRepository;
import ru.xdd.computer_store.repository.SaleRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> createCartForUser(user));
    }



    @Transactional
    public void addItemToCart(User user, Long productId, int quantity) {
        Cart cart = getCartByUser(user);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));

        // Проверяем, есть ли уже этот товар в корзине
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.increaseQuantity(quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.addItem(newItem);
        }

        cartRepository.save(cart);
    }

    @Transactional
    public void removeItemFromCart(User user, Long productId) {
        Cart cart = getCartByUser(user);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    @Transactional
    public void checkoutCart(User user) {
        Cart cart = getCartByUser(user);

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Корзина пуста");
        }

        // Проверяем наличие товаров на складе
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                throw new IllegalArgumentException("Недостаточно товара на складе: " + product.getTitle());
            }
        }

        // Уменьшаем запасы и создаём записи о продажах
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            // Уменьшаем запас товара
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            // Создаём запись о продаже
            Sale sale = new Sale();
            sale.setProduct(product);
            sale.setBuyer(user);
            sale.setManufacturer(product.getManufacturer());
            sale.setSalePrice(product.getPrice());
            sale.setPurchasePrice(product.getPurchasePrice());
            sale.setSaleDate(LocalDateTime.now());
            sale.setQuantity(quantity);

            saleRepository.save(sale);
        }

        // Очищаем корзину
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
}


