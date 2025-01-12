package ru.xdd.computer_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/403")
    public String accessDenied(Model model) {
        model.addAttribute("content", "403.ftlh"); // Указываем путь к шаблону главной страницы
        return "layout";

    }
}
