package ru.xdd.computer_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.xdd.computer_store.model.User;
import ru.xdd.computer_store.service.CartService;
import ru.xdd.computer_store.service.UserService;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;


    public HomeController(UserService userService, CartService cartService) {
        this.userService = userService;

    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        model.addAttribute("content", "home.ftlh"); // Путь к вашему шаблону главной страницы
        if (principal != null) {
            User currentUser = userService.getUserByPrincipal(principal);
            model.addAttribute("user", currentUser);
        }

        return "layout";
    }
}
