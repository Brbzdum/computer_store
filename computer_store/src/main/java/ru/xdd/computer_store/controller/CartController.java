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
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Cart cart = cartService.getCartByUser(user);

        model.addAttribute("cart", cart);
        return "cart/view";
    }

    @PostMapping("/add")
    public String addItemToCart(@RequestParam Long productId,
                                @RequestParam int quantity,
                                Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        cartService.addItemToCart(user, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItemFromCart(@RequestParam Long productId,
                                     Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        cartService.removeItemFromCart(user, productId);
        return "redirect:/cart";
    }
}

