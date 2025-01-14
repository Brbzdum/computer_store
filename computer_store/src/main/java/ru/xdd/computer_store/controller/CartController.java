package ru.xdd.computer_store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xdd.computer_store.model.Cart;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.service.CartService;
import ru.xdd.computer_store.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/users/login";
        }
        User user = userService.getUserByPrincipal(principal);
        Cart cart = cartService.getCartByUser(user);
        model.addAttribute("user", user); // Добавляем пользователя в модель
        model.addAttribute("cart", cart);
        model.addAttribute("cartItemCount", cart.getItems().size());
        model.addAttribute("content", "cart/view.ftlh");

        return "layout";
    }

    @PostMapping("/remove")
    public String removeItemFromCart(@RequestParam Long productId, Principal principal) {
        if (principal == null) {
            return "redirect:/users/login";
        }
        User user = userService.getUserByPrincipal(principal);
        cartService.removeItemFromCart(user, productId);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkoutCart(Principal principal) {
        if (principal == null) {
            return "redirect:/users/login";
        }

        User user = userService.getUserByPrincipal(principal);

        try {
            cartService.checkoutCart(user);
            return "redirect:/cart?success";
        } catch (IllegalArgumentException e) {
            return "redirect:/cart?error=" + e.getMessage();
        }
    }
}





