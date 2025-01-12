package ru.xdd.computer_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("content", "home.ftlh"); // Указываем путь к шаблону главной страницы
        return "layout"; // Возвращаем базовый шаблон
    }


}

