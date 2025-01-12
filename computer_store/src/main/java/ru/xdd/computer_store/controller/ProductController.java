package ru.xdd.computer_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.xdd.computer_store.model.Manufacturer;
import ru.xdd.computer_store.model.Product;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.service.CartService;
import ru.xdd.computer_store.service.ProductService;
import ru.xdd.computer_store.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;

    public ProductController(ProductService productService, CartService cartService, UserService userService) {
        this.productService = productService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        List<Manufacturer> manufacturers = productService.getAllManufacturers();

        model.addAttribute("content", "products/list.ftlh");
        model.addAttribute("products", products);
        model.addAttribute("manufacturers", manufacturers);
        return "layout";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден с ID: " + id));

        model.addAttribute("product", product);
        model.addAttribute("content", "products/view.ftlh");

        return "layout";
    }

    @PostMapping("/{id}/add-to-cart")
    public String addItemToCart(@PathVariable Long id,
                                @RequestParam int quantity,
                                Principal principal) {
        if (principal == null) {
            return "redirect:/users/login";
        }

        User user = userService.getUserByPrincipal(principal);
        cartService.addItemToCart(user, id, quantity);

        return "redirect:/cart"; // Перенаправляем на страницу корзины
    }
}





