package ru.xdd.computer_store.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.xdd.computer_store.model.Cart;
import ru.xdd.computer_store.model.CartItem;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.repository.CartRepository;
import ru.xdd.computer_store.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

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

    private Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
}

